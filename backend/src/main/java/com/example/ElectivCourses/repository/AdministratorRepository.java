package com.example.ElectivCourses.repository;

import com.example.ElectivCourses.entity.Administrator;
import com.example.ElectivCourses.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator,Long> {
}
