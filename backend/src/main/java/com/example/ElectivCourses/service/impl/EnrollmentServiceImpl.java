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
import com.example.ElectivCourses.repository.StudentRepository;
import com.example.ElectivCourses.service.EnrollmentService;
import com.example.ElectivCourses.service.EnrollmentAdministrationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentAdministrationService enrollmentAdministrationService;

    @Autowired
    private EnrollmentConverter enrollmentConverter;

    public boolean existsByCourseIdAndStudentId(Long courseId, Long studentId) {
        return enrollmentRepository.findAll().stream()
                .anyMatch(enrollment ->
                        enrollment.getCourse().getId().equals(courseId) &&
                                enrollment.getStudent().getId().equals(studentId)
                );
    }


    public void enrollStudentsToMandatoryCourses() {
        System.out.println("Running enrollStudents to automatically enroll students in mandatory courses...");

        List<Student> students = studentRepository.findAll();
        Map<Long, Integer> courseSeatsToDecrement = new HashMap<>();
        List<Enrollment> newEnrollments = new ArrayList<>();

        System.out.println("Number of students: " + students.size());

        for (Student student : students) {
            int studyYear = student.getStudyYear();
            System.out.println("Processing student ID: " + student.getId() + ", Study Year: " + studyYear);

            int maxMandatoryCourses = enrollmentAdministrationService.nrOfMandatoryCoursesByYear(studyYear);
            System.out.println("Maximum mandatory courses needed for this student: " + maxMandatoryCourses);

            List<Course> coursesByStudyYear = courseRepository.findByStudyYear(studyYear);

            List<Course> mandatoryCourses = coursesByStudyYear.stream()
                    .filter(course -> course.getCategory().equals("mandatory"))
                    .toList();

            System.out.println("Found " + mandatoryCourses.size() + " mandatory courses for study year " + studyYear);

            if (mandatoryCourses.size() < maxMandatoryCourses) {
                System.out.println("Not enough mandatory courses available for student ID: " + student.getId());
                continue;
            }


            for (int i = 0; i < maxMandatoryCourses; i++) {
                Course course = mandatoryCourses.get(i);


                boolean alreadyEnrolled = existsByCourseIdAndStudentId(course.getId(), student.getId());
                System.out.println("Checking enrollment for student ID: " + student.getId() + ", Course ID: " + course.getId() + " - Already Enrolled: " + alreadyEnrolled);

                if (!alreadyEnrolled) {
                    Enrollment newEnrollment = new Enrollment();
                    newEnrollment.setCourse(course);
                    newEnrollment.setStudent(student);
                    newEnrollment.setStatus(EnrollmentStatus.ENROLLED);
                    newEnrollments.add(newEnrollment);


                    courseSeatsToDecrement.put(course.getId(), courseSeatsToDecrement.getOrDefault(course.getId(), 0) + 1);
                    System.out.println("Enrolling student ID: " + student.getId() + " in course ID: " + course.getId());
                } else {
                    System.out.println("Student ID: " + student.getId() + " is already enrolled in course ID: " + course.getId());
                }
            }
        }

        System.out.println("New enrollments to be saved: " + newEnrollments.size());

        for (Map.Entry<Long, Integer> entry : courseSeatsToDecrement.entrySet()) {
            Course course = courseRepository.findById(entry.getKey())
                    .orElseThrow(() -> new IllegalStateException("Course not found with ID: " + entry.getKey()));

            int seatsToDecrement = entry.getValue();
            System.out.println("Adjusting seats for course ID: " + course.getId() + ". Seats to decrement: " + seatsToDecrement);

            if (course.getMaxStudents() < seatsToDecrement) {
                throw new IllegalStateException("Course with ID: " + course.getId() + " does not have enough seats available.");
            }

            course.setMaxStudents(course.getMaxStudents() - seatsToDecrement);
            courseRepository.save(course);
            System.out.println("Updated course ID: " + course.getId() + " - New available seats: " + course.getMaxStudents());
        }

        enrollmentRepository.saveAll(newEnrollments);
        System.out.println("Saved all new enrollments.");
    }



    @EventListener(ApplicationReadyEvent.class)
    public void fixEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();

        Map<Long, Integer> courseSeatsToDecrement = new HashMap<>();

        for (Enrollment enrollment : enrollments) {
            Course course = enrollment.getCourse();
            Student student = enrollment.getStudent();

            if (course.getCategory().equals("mandatory") && !enrollment.getStatus().equals(EnrollmentStatus.ENROLLED)) {
                enrollment.setStatus(EnrollmentStatus.ENROLLED);

                courseSeatsToDecrement.put(course.getId(), courseSeatsToDecrement.getOrDefault(course.getId(), 0) + 1);
            }

            if (student.getStudyYear() != course.getStudyYear()) {
                throw new IllegalStateException("Study year mismatch for student ID: " + student.getId() + " and course ID: " + course.getId());
            }

            int maxMandatoryCourses = enrollmentAdministrationService.nrOfMandatoryCoursesByYear(student.getStudyYear());
            long currentMandatoryCourses = enrollments.stream()
                    .filter(e -> e.getStudent().getId().equals(student.getId()) && e.getCourse().getCategory().equals("mandatory"))
                    .count();

            if (currentMandatoryCourses > maxMandatoryCourses) {
                throw new IllegalStateException("Student has exceeded the max number of mandatory courses for their study year: " + student.getId());
            }
        }

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

        enrollStudentsToMandatoryCourses();

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

        Course course = courseRepository.findById(newEnrollment.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.getMaxStudents() <= 0) {
            throw new IllegalArgumentException("No more spaces available in the course.");
        }

        course.setMaxStudents(course.getMaxStudents() - 1);

        courseRepository.save(course);

        newEnrollment = enrollmentRepository.save(newEnrollment);

        return EnrollmentConverter.toDTO(newEnrollment);
    }

    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream().map(EnrollmentConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteEnrollment(Long studentId, Long courseId) {
        Optional<Enrollment> existentEnrollment = enrollmentRepository.findAll().stream()
                .filter(enrollment -> Objects.equals(enrollment.getStudent().getId(), studentId))
                .filter(enrollment -> Objects.equals(enrollment.getCourse().getId(), courseId))
                .findAny();

        existentEnrollment.ifPresent(enrollment -> enrollmentRepository.delete(enrollment));

    }

    @Override
    public long getNrOfCurrentApplications(Long courseId) {
        return enrollmentRepository.findAll().stream()
                .filter(enrollment -> Objects.equals(enrollment.getCourse().getId(), courseId)).filter(enrollment -> enrollment.getStatus() == EnrollmentStatus.PENDING).count();
    }

    @Override
    public List<StudentDTO> getStudentsPendingEnrollmentToCourse(Long courseId) {
        return enrollmentRepository.findAll().stream()
                .filter(enrollment -> Objects.equals(enrollment.getCourse().getId(), courseId)).filter(enrollment -> enrollment.getStatus() == EnrollmentStatus.PENDING)
                .map(enrollment -> StudentConverter.toDTO(enrollment.getStudent())).collect(Collectors.toList());
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
