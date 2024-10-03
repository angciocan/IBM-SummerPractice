package com.example.electivecourses.controller;


import com.example.electivecourses.model.entity.EnrollmentAdministration;
import com.example.electivecourses.service.EnrollmentAdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
