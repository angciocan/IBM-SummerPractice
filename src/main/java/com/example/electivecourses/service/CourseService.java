package com.example.electivecourses.service;

import com.example.electivecourses.model.dto.CourseDTO;
import com.example.electivecourses.model.entity.Course;

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
