package com.example.electivecourses.service;

import com.example.electivecourses.model.entity.EnrollmentAdministration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface EnrollmentAdministrationService {
    List<EnrollmentAdministration> getAllEnrollmentAdministrations();
    int nrOfMandatoryCoursesByYear(int studyYear);
    int nrOfElectiveCoursesByYear(int studyYear);
    EnrollmentAdministration createEnrollmentAdministration(int studyYear, int nrOfMandatoryCourses, int nrOfElectivCourses);
    void deleteEnrollmentAdministration(int enrollmentAdministrationId);
    void setEnrollmentPeriod(LocalDate startTime, LocalDate endTime);
    ArrayList<LocalDate> getEnrollmentPeriod();
}
