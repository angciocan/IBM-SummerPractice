package com.example.ElectivCourses.service;

import com.example.ElectivCourses.model.dto.CourseDTO;
import com.example.ElectivCourses.model.entity.Course;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourses();
    List<CourseDTO> getCoursesByStudyYear(int studyYear);
    List<CourseDTO> getCoursesByMaxStudents(int maxStudents);
    List<CourseDTO> getCoursesByCategory(String category);
    CourseDTO getCourseById(Long id);
    CourseDTO updateCourse(Long id, Course courseToUpdate);
    void createCourse(Course courseToCreate);
    void deleteCourse(Long id);
}
