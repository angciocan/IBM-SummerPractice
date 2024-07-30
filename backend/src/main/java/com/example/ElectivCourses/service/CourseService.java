package com.example.ElectivCourses.service;

import com.example.ElectivCourses.Model.dto.CourseDTO;
import com.example.ElectivCourses.Model.entity.Course;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourses();
    List<CourseDTO> getCoursesByStudyYear(int studyYear);
    List<CourseDTO> getCoursesByMaxStudents(int maxStudents);
    List<CourseDTO> getCoursesByCategory(String category);
    CourseDTO getCourseById(Long id);
    List<CourseDTO> getCoursesByStudentIdApplications(Long studentId);
}
