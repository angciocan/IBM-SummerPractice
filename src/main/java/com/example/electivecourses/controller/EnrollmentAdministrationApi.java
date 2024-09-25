package com.example.electivecourses.controller;


import com.example.electivecourses.model.entity.EnrollmentAdministration;
import com.example.electivecourses.service.EnrollmentAdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/enrollmentAdministration")
@CrossOrigin(origins = "http://localhost:4200")
public class EnrollmentAdministrationApi {
    @Autowired
    EnrollmentAdministrationService enrollmentAdministrationService;

    @GetMapping("/")
    public List<EnrollmentAdministration> getAllEnrollmentAdministrations() {
        return enrollmentAdministrationService.getAllEnrollmentAdministrations();
    }

    @GetMapping("/nrOfMandatoryCoursesByYear/{studyYear}")
    public int nrOfMandatoryCoursesByYear(@PathVariable("studyYear") int studyYear) {
        return enrollmentAdministrationService.nrOfMandatoryCoursesByYear(studyYear);
    }

    @GetMapping("/nrOfElectiveCoursesByYear/{studyYear}")
    public int nrOfElectiveCoursesByYear(@PathVariable("studyYear") int studyYear) {
        return enrollmentAdministrationService.nrOfElectiveCoursesByYear(studyYear);
    }


    @PostMapping("/create/{studyYear}/{nrOfMandatoryCourses}/{nrOfElectiveCourses}")
    EnrollmentAdministration createEnrollmentAdministration(@PathVariable("studyYear") int studyYear, @PathVariable("nrOfMandatoryCourses") int nrOfMandatoryCourses, @PathVariable("nrOfElectiveCourses") int nrOfElectiveCourses) {
        return enrollmentAdministrationService.createEnrollmentAdministration(studyYear, nrOfMandatoryCourses, nrOfElectiveCourses);
    }

    @DeleteMapping("/delete/{studyYear}")
    void deleteEnrollmentAdministration(@PathVariable("studyYear") int enrollmentAdministrationId) {
        enrollmentAdministrationService.deleteEnrollmentAdministration(enrollmentAdministrationId);
    }

    @PostMapping("/setEnrollmentPeriod/{startTime}/{endTime}")
    void setEnrollmentPeriod(@PathVariable("startTime")LocalDate startTime, @PathVariable("endTime") LocalDate endTime) {
        enrollmentAdministrationService.setEnrollmentPeriod(startTime, endTime);
    }

    @GetMapping("/getEnrollmentPeriod")
    ArrayList<LocalDate> getEnrollmentPeriod() {
        return enrollmentAdministrationService.getEnrollmentPeriod();
    }
}
