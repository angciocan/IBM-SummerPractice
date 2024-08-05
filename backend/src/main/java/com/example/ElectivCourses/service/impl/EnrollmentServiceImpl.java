package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.dto.CourseDTO;
import com.example.ElectivCourses.Model.dto.EnrollmentDTO;
import com.example.ElectivCourses.Model.dto.StudentDTO;
import com.example.ElectivCourses.Model.dto.TeacherDTO;
import com.example.ElectivCourses.Model.entity.Enrollment;
import com.example.ElectivCourses.Model.entity.EnrollmentStatus;
import com.example.ElectivCourses.converter.EnrollmentConverter;
import com.example.ElectivCourses.converter.StudentConverter;
import com.example.ElectivCourses.repository.EnrollmentRepository;
import com.example.ElectivCourses.service.EnrollmentService;
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
    private EnrollmentConverter enrollmentConverter;

    @Override
    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        Enrollment newEnrollment = enrollmentConverter.toEntity(enrollmentDTO);
        newEnrollment.setStatus(EnrollmentStatus.PENDING);

        Enrollment finalNewEnrollment = newEnrollment;
        Optional<Enrollment> alreadyExists = enrollmentRepository.findAll().stream()
                .filter(enrollment ->
                        enrollment.getStudent().getId().equals(finalNewEnrollment.getStudent().getId()) &&
                                enrollment.getCourse().getId().equals(finalNewEnrollment.getCourse().getId())
                ).findAny();

        if (alreadyExists.isPresent()) {
            return EnrollmentConverter.toDTO(alreadyExists.get());
        }

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
