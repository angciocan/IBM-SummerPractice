package com.example.ElectivCourses.repository;

import com.example.ElectivCourses.Model.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    void deleteById(Long id);
}
