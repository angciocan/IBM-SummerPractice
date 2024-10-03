package com.example.electivecourses.repository;

import com.example.electivecourses.model.entity.Enrollment;
import com.example.electivecourses.model.entity.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentManagementRepository extends JpaRepository<Enrollment,Long> {
    List<Enrollment> findByStatus(EnrollmentStatus status);
}
