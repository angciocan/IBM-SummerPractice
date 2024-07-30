package com.example.ElectivCourses.repository;

import com.example.ElectivCourses.Model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
}
