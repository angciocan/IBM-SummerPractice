package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.entity.EnrollmentAdministration;
import com.example.ElectivCourses.repository.EnrollmentAdministrationRepository;
import com.example.ElectivCourses.service.EnrollmentAdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EnrollmentAdministrationServiceImpl implements EnrollmentAdministrationService {
    @Autowired
    private EnrollmentAdministrationRepository enrollmentAdministrationRepository;

    @Override
    public List<EnrollmentAdministration> getAllEnrollmentAdministrations() {
        return enrollmentAdministrationRepository.findAll();
    }

    @Override
    public int nrOfMandatoryCoursesByYear(int studyYear) {
        return enrollmentAdministrationRepository.findAll().stream()
                .filter(enrollmentAdministration -> enrollmentAdministration.getStudyYear() == studyYear)
                .findAny()
                .map(EnrollmentAdministration::getNrOfMandatoryCourses)
                .orElseThrow(() -> new NoSuchElementException("No EnrollmentAdministration found for study year: " + studyYear));
    }

    @Override
    public int nrOfElectiveCoursesByYear(int studyYear) {
        return enrollmentAdministrationRepository.findAll().stream()
                .filter(enrollmentAdministration -> enrollmentAdministration.getStudyYear() == studyYear)
                .findAny()
                .map(EnrollmentAdministration::getNrOfElectiveCourses)
                .orElseThrow(() -> new NoSuchElementException("No EnrollmentAdministration found for study year: " + studyYear));
    }

    @Override
    public EnrollmentAdministration createEnrollmentAdministration(int studyYear, int nrOfMandatoryCourses, int nrOfElectiveCourses) {
        Optional<EnrollmentAdministration> enrollmentAdministration1 = enrollmentAdministrationRepository.findAll().stream()
                .filter(enrollmentAdministration -> enrollmentAdministration.getStudyYear() == studyYear).findAny();

        if (enrollmentAdministration1.isPresent()) {
            enrollmentAdministration1.get().setNrOfMandatoryCourses(nrOfMandatoryCourses);
            enrollmentAdministration1.get().setNrOfElectiveCourses(nrOfElectiveCourses);

            return enrollmentAdministrationRepository.save(enrollmentAdministration1.get());
        }
        else {
            return enrollmentAdministrationRepository.save(new EnrollmentAdministration(studyYear, nrOfMandatoryCourses,nrOfElectiveCourses));
        }
    }

    @Override
    public void deleteEnrollmentAdministration(int studyYear) {
        Optional<EnrollmentAdministration> enrollmentAdministration1 = enrollmentAdministrationRepository.findAll().stream()
                .filter(enrollmentAdministration -> enrollmentAdministration.getStudyYear() == studyYear).findAny();

        enrollmentAdministration1.ifPresent(enrollmentAdministration -> enrollmentAdministrationRepository.delete(enrollmentAdministration));
    }

    @Override
    public void setEnrollmentPeriod(LocalDate startTime, LocalDate endTime) {
        EnrollmentAdministration.setStartTime(startTime);
        EnrollmentAdministration.setEndTime(endTime);
    }

    @Override
    public ArrayList<LocalDate> getEnrollmentPeriod() {
        ArrayList<LocalDate> enrollmentPeriod = new ArrayList<>();
        enrollmentPeriod.add(EnrollmentAdministration.getStartTime());
        enrollmentPeriod.add(EnrollmentAdministration.getEndTime());
        return enrollmentPeriod;
    }
}
