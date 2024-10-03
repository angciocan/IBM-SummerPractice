package com.example.ElectivCourses.service;

import com.example.ElectivCourses.model.entity.EnrollmentAdministration;


import java.util.List;

public interface EnrollmentAdministrationService {
    List<EnrollmentAdministration> getAllEnrollmentAdministrations();
    int nrOfMandatoryCoursesByYear(int studyYear);
    int nrOfElectiveCoursesByYear(int studyYear);
}
