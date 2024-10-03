package com.example.ElectivCourses.controller;

import com.example.ElectivCourses.model.dto.CourseDTO;
import com.example.ElectivCourses.model.dto.StudentDTO;
import com.example.ElectivCourses.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ElectivCourses.model.dto.EnrollmentDTO;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
@CrossOrigin(origins = "http://localhost:4200")
public class EnrollmentApi {
    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/")
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollments();
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EnrollmentDTO> createEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO enrollmentDTO2 = enrollmentService.createEnrollment(enrollmentDTO);
        return new ResponseEntity<>(enrollmentDTO2, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{studentId}/{courseId}")
    public void deleteEnrollment(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {
        enrollmentService.deleteEnrollment(studentId, courseId);
    }

    @GetMapping("/nr-of-current-applications/{courseId}")
    public long getNrOfCurrentApplications(@PathVariable("courseId") Long courseId) {
        return enrollmentService.getNrOfCurrentApplications(courseId);
    }

    @GetMapping("/students-enrolled-to-course/{courseId}")
    public  ResponseEntity<List<StudentDTO>> getStudentsEnrolledToCourse(@PathVariable("courseId") Long courseId) {
        List<StudentDTO> students = enrollmentService.getStudentsEnrolledToCourse(courseId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping("/students-pending-enrollment-to-course/{courseId}")
    public ResponseEntity<List<StudentDTO>> getStudentsPendingEnrollmentToCourse(@PathVariable("courseId") Long courseId) {
        List<StudentDTO> students = enrollmentService.getStudentsPendingEnrollmentToCourse(courseId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping("/courses-of-the-student/{studentId}")
    public ResponseEntity<List<CourseDTO>> getCoursesForStudent(@PathVariable Long studentId) {
        List<CourseDTO> courses = enrollmentService.getCoursesForStudent(studentId);
        return ResponseEntity.ok(courses);
    }


}