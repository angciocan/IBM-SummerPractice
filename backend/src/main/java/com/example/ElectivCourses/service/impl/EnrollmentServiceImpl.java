package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.dto.EnrollmentDTO;
import com.example.ElectivCourses.Model.entity.Enrollment;
import com.example.ElectivCourses.converter.EnrollmentConverter;
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
        Enrollment newEnrollment =  enrollmentConverter.toEntity(enrollmentDTO);
        Optional<Enrollment> alreadyExists = enrollmentRepository.findAll().stream()
                .filter(enrollment -> (enrollment.getStudent() == newEnrollment.getStudent() && enrollment.getCourse() == newEnrollment.getCourse())).findAny();

        if (alreadyExists.isPresent()) {
            return EnrollmentConverter.toDTO(alreadyExists.get());
        }

//        long timestamp = System.currentTimeMillis();
//        int randomNumber = new Random().nextInt(999999); // Six-digit random number
//
//        newEnrollment.setId(Long.parseLong(timestamp + String.format("%04d", randomNumber)));

        enrollmentRepository.save(newEnrollment);
        System.out.println(newEnrollment.getId());
        return EnrollmentConverter.toDTO(newEnrollment);
//        return EnrollmentConverter.toDTO(enrollmentRepository.save(enrollmentConverter.toEntity(enrollmentDTO)));
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
}
