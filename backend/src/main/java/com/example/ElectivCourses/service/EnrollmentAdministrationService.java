package com.example.ElectivCourses.service;

import com.example.ElectivCourses.Model.entity.EnrollmentAdministration;
import org.springframework.web.bind.annotation.PathVariable;

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
