package com.example.electivecourses.service;

import com.example.electivecourses.model.entity.EnrollmentAdministration;


import java.util.List;

public interface EnrollmentAdministrationService {
    List<EnrollmentAdministration> getAllEnrollmentAdministrations();
    int nrOfMandatoryCoursesByYear(int studyYear);
    int nrOfElectiveCoursesByYear(int studyYear);
    EnrollmentAdministration createEnrollmentAdministration(int studyYear, int nrOfMandatoryCourses, int nrOfElectivCourses);
    void deleteEnrollmentAdministration(int enrollmentAdministrationId);
}
