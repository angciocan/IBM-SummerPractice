package com.example.ElectivCourses.service;

import com.example.ElectivCourses.Model.dto.EnrollmentDTO;
import com.example.ElectivCourses.Model.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO);
    List<EnrollmentDTO> getAllEnrollments();
    void deleteEnrollment(Long studentId, Long courseId);
}
