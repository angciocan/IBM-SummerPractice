package com.example.electivecourses.controller;


import com.example.electivecourses.service.EnrollmentPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("enrollmentPeriod")
@CrossOrigin("http://localhost:4200")
public class EnrollmentPeriodApi {

    @Autowired
    EnrollmentPeriodService enrollmentPeriodService;

    @PostMapping("/open")
    public ResponseEntity<String> openEnrollmentPeriod() {
        try {
            enrollmentPeriodService.openEnrollmentPeriod();
            return ResponseEntity.ok("Enrollment period opened successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while opening the enrollment period.");
        }
    }

    @PostMapping("/close")
    public ResponseEntity<String> closeEnrollmentPeriod() {
        try {
            enrollmentPeriodService.closeEnrollmentPeriod();
            return ResponseEntity.ok("Enrollment period closed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while closing the enrollment period.");
        }
    }

    @GetMapping("/is-open")
    public ResponseEntity<Boolean> isEnrollmentPeriodOpen() {
        try {
            boolean isOpen = enrollmentPeriodService.isEnrollmentPeriodOpen();
            return ResponseEntity.ok(isOpen);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

}
