package com.example.electivecourses.repository;

import com.example.electivecourses.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findByStudyYear(int year);
    List<Course> findByMaxStudents(int maxStudents);
    List<Course> findByCategory(String category);
}

