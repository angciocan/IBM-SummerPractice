package com.example.ElectivCourses.controller;

import com.example.ElectivCourses.Model.entity.Enrollment;
import com.example.ElectivCourses.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ElectivCourses.Model.dto.EnrollmentDTO;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@CrossOrigin(origins = "http://localhost:4200")
public class EnrollmentApi {
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/create")
    public ResponseEntity<EnrollmentDTO> createEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO enrollmentDTO2 = enrollmentService.createEnrollment(enrollmentDTO);
        return new ResponseEntity<>(enrollmentDTO2, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollments();
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{studentId}-{courseId}")
    public void deleteEnrollment(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {
        enrollmentService.deleteEnrollment(studentId, courseId);
    }
}
