package com.example.ElectivCourses.service;

import com.example.ElectivCourses.Model.dto.CourseDTO;
import com.example.ElectivCourses.Model.dto.EnrollmentDTO;
import com.example.ElectivCourses.Model.dto.StudentDTO;
import com.example.ElectivCourses.Model.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO);
    List<EnrollmentDTO> getAllEnrollments();
    void deleteEnrollment(Long studentId, Long courseId);
    long getNrOfCurrentApplications(Long courseId);
    List<StudentDTO> getStudentsEnrolledToCourse(Long courseId);
    List<CourseDTO> getCoursesForStudent(Long studentId);
    void fixEnrollments();
    void enrollStudents();
    boolean existsByCourseIdAndStudentId(Long courseId,Long studentId);
    List<StudentDTO> getStudentsPendingEnrollmentToCourse(Long courseId);
}
