package com.example.ElectivCourses.repository;

import com.example.ElectivCourses.Model.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator,Long> {
}
