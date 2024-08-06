package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.entity.EnrollmentAdministration;
import com.example.ElectivCourses.repository.EnrollmentAdministrationRepository;
import com.example.ElectivCourses.service.EnrollmentAdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    public int nrOfCoursesByStudyYear(int studyYear) {
        return enrollmentAdministrationRepository.findAll().stream().filter(enrollmentAdministration -> enrollmentAdministration.getStudyYear() == studyYear).findAny().get().getNrOfCourses();
    }

    @Override
    public EnrollmentAdministration createEnrollmentAdministration(int studyYear, int nrOfCourses) {
        Optional<EnrollmentAdministration> enrollmentAdministration1 = enrollmentAdministrationRepository.findAll().stream()
                .filter(enrollmentAdministration -> enrollmentAdministration.getStudyYear() == studyYear).findAny();

        if (enrollmentAdministration1.isPresent()) {
            enrollmentAdministration1.get().setNrOfCourses(nrOfCourses);
            return enrollmentAdministrationRepository.save(enrollmentAdministration1.get());
        }
        else {
            return enrollmentAdministrationRepository.save(new EnrollmentAdministration(studyYear, nrOfCourses));
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
