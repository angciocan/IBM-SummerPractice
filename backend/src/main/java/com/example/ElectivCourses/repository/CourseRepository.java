package com.example.ElectivCourses.repository;

import com.example.ElectivCourses.entity.Course;
import com.example.ElectivCourses.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}

