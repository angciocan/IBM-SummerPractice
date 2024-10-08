package com.example.ElectivCourses.service;

import com.example.ElectivCourses.model.dto.CourseDTO;
import com.example.ElectivCourses.model.dto.EnrollmentDTO;
import com.example.ElectivCourses.model.dto.StudentDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO);
    List<EnrollmentDTO> getAllEnrollments();
    List<EnrollmentDTO> getAllPendingEnrollments();
    void deleteEnrollment(Long studentId, Long courseId);
    long getNrOfCurrentApplications(Long courseId);
    List<StudentDTO> getStudentsEnrolledToCourse(Long courseId);
    List<CourseDTO> getCoursesForStudent(Long studentId);
    void enrollStudentsToMandatoryCourses();
    boolean existsByCourseIdAndStudentId(Long courseId,Long studentId);
    List<StudentDTO> getStudentsPendingEnrollmentToCourse(Long courseId);
    EnrollmentDTO getEnrollmentByStudentAndCourseId(Long studentId, Long courseId);

}
