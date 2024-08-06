package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.dto.CourseDTO;
import com.example.ElectivCourses.Model.dto.EnrollmentDTO;
import com.example.ElectivCourses.Model.dto.StudentDTO;
import com.example.ElectivCourses.Model.dto.TeacherDTO;
import com.example.ElectivCourses.Model.entity.Course;
import com.example.ElectivCourses.Model.entity.Enrollment;
import com.example.ElectivCourses.Model.entity.EnrollmentStatus;
import com.example.ElectivCourses.converter.EnrollmentConverter;
import com.example.ElectivCourses.converter.StudentConverter;
import com.example.ElectivCourses.repository.CourseRepository;
import com.example.ElectivCourses.repository.EnrollmentRepository;
import com.example.ElectivCourses.service.EnrollmentService;
import com.example.ElectivCourses.service.EnrollmentAdministrationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
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
