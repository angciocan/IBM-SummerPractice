package com.example.ElectivCourses.repository;

import com.example.ElectivCourses.Model.entity.Enrollment;
import com.example.ElectivCourses.Model.entity.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentManagementRepository extends JpaRepository<Enrollment,Long> {
    List<Enrollment> findByStatus(EnrollmentStatus status);
}
