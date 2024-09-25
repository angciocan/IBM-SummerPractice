package com.example.electivecourses.service;

import com.example.electivecourses.model.dto.CourseDTO;
import com.example.electivecourses.model.dto.EnrollmentDTO;
import com.example.electivecourses.model.dto.StudentDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO);
    List<EnrollmentDTO> getAllEnrollments();
    void deleteEnrollment(Long studentId, Long courseId);
    long getNrOfCurrentApplications(Long courseId);
    List<StudentDTO> getStudentsEnrolledToCourse(Long courseId);
    List<CourseDTO> getCoursesForStudent(Long studentId);
    void fixEnrollments();
    void enrollStudentsToMandatoryCourses();
    boolean existsByCourseIdAndStudentId(Long courseId,Long studentId);
    List<StudentDTO> getStudentsPendingEnrollmentToCourse(Long courseId);
}
