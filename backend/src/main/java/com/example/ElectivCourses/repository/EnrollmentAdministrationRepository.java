package com.example.ElectivCourses.repository;

import com.example.ElectivCourses.model.entity.EnrollmentAdministration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentAdministrationRepository extends JpaRepository<EnrollmentAdministration,Long> {
}
