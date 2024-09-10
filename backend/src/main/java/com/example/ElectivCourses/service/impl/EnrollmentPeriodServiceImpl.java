package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.entity.EnrollmentPeriod;
import com.example.ElectivCourses.repository.EnrollmentPeriodRepository;
import com.example.ElectivCourses.service.EnrollmentPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentPeriodServiceImpl implements EnrollmentPeriodService {

    @Autowired
    private EnrollmentPeriodRepository enrollmentPeriodRepository;

    @Override
    public void openEnrollmentPeriod() {
        EnrollmentPeriod period = enrollmentPeriodRepository.findOrCreateSingleton();
        period.setOpen(true);
        enrollmentPeriodRepository.save(period);
    }

    @Override
    public void closeEnrollmentPeriod() {
        EnrollmentPeriod period = enrollmentPeriodRepository.findOrCreateSingleton();
        period.setOpen(false);
        enrollmentPeriodRepository.save(period);
    }

    @Override
    public boolean isEnrollmentPeriodOpen() {
        return enrollmentPeriodRepository.findOrCreateSingleton().isOpen();
    }

}
