package com.example.ElectivCourses.controller;

import com.example.ElectivCourses.service.EnrollmentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollmentManagement")
@CrossOrigin(origins = "http://localhost:4200")
public class EnrollmentManagementApi {

    @Autowired
    private EnrollmentManagementService enrollmentManagementService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/process-pending-enrollment")
    public ResponseEntity<String> processPendingEnrollments() {
        try {
            enrollmentManagementService.processPendingEnrollments();
            return ResponseEntity.ok("Pending enrollments processed successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Enrollment period is still open.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing pending enrollments.");
        }
    }

}
