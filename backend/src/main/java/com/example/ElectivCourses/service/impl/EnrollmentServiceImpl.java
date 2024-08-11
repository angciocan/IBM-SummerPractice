package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.dto.CourseDTO;
import com.example.ElectivCourses.Model.dto.EnrollmentDTO;
import com.example.ElectivCourses.Model.dto.StudentDTO;
import com.example.ElectivCourses.Model.dto.TeacherDTO;
import com.example.ElectivCourses.Model.entity.Course;
import com.example.ElectivCourses.Model.entity.Enrollment;
import com.example.ElectivCourses.Model.entity.EnrollmentStatus;
import com.example.ElectivCourses.Model.entity.Student;
import com.example.ElectivCourses.converter.EnrollmentConverter;
import com.example.ElectivCourses.converter.StudentConverter;
import com.example.ElectivCourses.repository.CourseRepository;
import com.example.ElectivCourses.repository.EnrollmentRepository;
import com.example.ElectivCourses.service.EnrollmentService;
import com.example.ElectivCourses.service.EnrollmentAdministrationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EnrollmentAdministrationService enrollmentAdministrationService;

    @Autowired
    private EnrollmentConverter enrollmentConverter;

    @PostConstruct
    public void fixEnrollments() {
        System.out.println("Running fixEnrollments to ensure data consistency...");

        List<Enrollment> enrollments = enrollmentRepository.findAll();
        Map<Long, Integer> courseSeatsToDecrement = new HashMap<>();

        for (Enrollment enrollment : enrollments) {
            Course course = enrollment.getCourse();
            Student student = enrollment.getStudent();

            // Check if course is mandatory and ensure status is set to ENROLLED
            if (course.getCategory().equals("mandatory") && !enrollment.getStatus().equals(EnrollmentStatus.ENROLLED)) {
                enrollment.setStatus(EnrollmentStatus.ENROLLED);

                // Track the courses for which maxStudents need to be decremented
                courseSeatsToDecrement.put(course.getId(), courseSeatsToDecrement.getOrDefault(course.getId(), 0) + 1);
            }

            // Validate that the student's study year matches the course's study year
            if (student.getStudyYear() != course.getStudyYear()) {
                throw new IllegalStateException("Study year mismatch for student ID: " + student.getId() + " and course ID: " + course.getId());
            }

            // Validate that the student has not exceeded the max number of mandatory courses
            int maxMandatoryCourses = enrollmentAdministrationService.nrOfMandatoryCoursesByYear(student.getStudyYear());
            long currentMandatoryCourses = enrollments.stream()
                    .filter(e -> e.getStudent().getId().equals(student.getId()) && e.getCourse().getCategory().equals("mandatory"))
                    .count();

            if (currentMandatoryCourses > maxMandatoryCourses) {
                throw new IllegalStateException("Student has exceeded the max number of mandatory courses for their study year: " + student.getId());
            }
        }

        // Decrement the maxStudents count for the affected courses
        for (Map.Entry<Long, Integer> entry : courseSeatsToDecrement.entrySet()) {
            Course course = courseRepository.findById(entry.getKey())
                    .orElseThrow(() -> new IllegalStateException("Course not found with ID: " + entry.getKey()));

            int seatsToDecrement = entry.getValue();
            if (course.getMaxStudents() < seatsToDecrement) {
                throw new IllegalStateException("Course with ID: " + course.getId() + " does not have enough seats available.");
            }

            course.setMaxStudents(course.getMaxStudents() - seatsToDecrement);
            courseRepository.save(course);
        }

        // Save all updated enrollments
        enrollmentRepository.saveAll(enrollments);
    }

    @Override
    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        Enrollment newEnrollment = enrollmentConverter.toEntity(enrollmentDTO);
        newEnrollment.setStatus(EnrollmentStatus.PENDING);

        int studentStudyYear = newEnrollment.getStudent().getStudyYear();
        int courseStudyYear = newEnrollment.getCourse().getStudyYear();

        if (studentStudyYear != courseStudyYear) {
            throw new IllegalArgumentException("The study year of the student does not match the study year of the course.");
        }

        int maxMandatoryCourses = enrollmentAdministrationService.nrOfMandatoryCoursesByYear(studentStudyYear);

        Enrollment finalNewEnrollment1 = newEnrollment;
        long currentMandatoryCourses = enrollmentRepository.findAll().stream()
                .filter(enrollment ->
                        enrollment.getStudent().getId().equals(finalNewEnrollment1.getStudent().getId()) &&
                                enrollment.getCourse().getCategory().equals("mandatory")
                ).count();

        if (newEnrollment.getCourse().getCategory().equals("mandatory") && currentMandatoryCourses >= maxMandatoryCourses) {
            throw new IllegalArgumentException("The student has already reached the maximum number of mandatory courses for their study year.");
        }
        if(newEnrollment.getCourse().getCategory().equals("mandatory")){
            newEnrollment.setStatus(EnrollmentStatus.ENROLLED);
        }

        Enrollment finalNewEnrollment = newEnrollment;
        Optional<Enrollment> alreadyExists = enrollmentRepository.findAll().stream()
                .filter(enrollment ->
                        enrollment.getStudent().getId().equals(finalNewEnrollment.getStudent().getId()) &&
                                enrollment.getCourse().getId().equals(finalNewEnrollment.getCourse().getId())
                ).findAny();

        if (alreadyExists.isPresent()) {
            return EnrollmentConverter.toDTO(alreadyExists.get());
        }

        // Fetch the course entity to update maxStudents
        Course course = courseRepository.findById(newEnrollment.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Check if there is space left in the course
        if (course.getMaxStudents() <= 0) {
            throw new IllegalArgumentException("No more spaces available in the course.");
        }

        // Decrease the maxStudents by 1
        course.setMaxStudents(course.getMaxStudents() - 1);

        // Save the updated course
        courseRepository.save(course);

        // Save the new enrollment
        newEnrollment = enrollmentRepository.save(newEnrollment);

        return EnrollmentConverter.toDTO(newEnrollment);
    }



    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        fixEnrollments();
        return enrollmentRepository.findAll().stream().map(EnrollmentConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteEnrollment(Long studentId, Long courseId) {
        Optional<Enrollment> existentEnrollment = enrollmentRepository.findAll().stream()
                .filter(enrollment -> Objects.equals(enrollment.getStudent().getId(), studentId))
                .filter(enrollment -> Objects.equals(enrollment.getCourse().getId(), courseId))
                .findAny();


        existentEnrollment.ifPresent(enrollment -> enrollmentRepository.delete(enrollment));



//        existentEnrollment.ifPresent(enrollment -> enrollmentRepository.delete(enrollment));

    }

    @Override
    public long getNrOfCurrentApplications(Long courseId) {
        return enrollmentRepository.findAll().stream()
                .filter(enrollment -> Objects.equals(enrollment.getCourse().getId(), courseId)).filter(enrollment -> enrollment.getStatus() == EnrollmentStatus.PENDING).count();
    }

    @Override
    public List<StudentDTO> getStudentsEnrolledToCourse(Long courseId) {
        return enrollmentRepository.findAll().stream()
                .filter(enrollment -> Objects.equals(enrollment.getCourse().getId(), courseId)).filter(enrollment -> enrollment.getStatus() == EnrollmentStatus.ENROLLED)
                .map(enrollment -> StudentConverter.toDTO(enrollment.getStudent())).collect(Collectors.toList());
    }
    @Override
    public List<CourseDTO> getCoursesForStudent(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .map(enrollment -> {
                    TeacherDTO teacherDTO = new TeacherDTO(
                            enrollment.getCourse().getTeacher().getId(),
                            enrollment.getCourse().getTeacher().getName()
                    );
                    return new CourseDTO(
                            enrollment.getCourse().getCourseName(),
                            enrollment.getCourse().getMaxStudents(),
                            enrollment.getCourse().getStudyYear(),
                            enrollment.getCourse().getCategory(),
                            enrollment.getCourse().getDayOfWeek(),
                            enrollment.getCourse().getTime(),
                            teacherDTO,
                            enrollment.getCourse().getId()
                    );
                })
                .collect(Collectors.toList());
    }
}
