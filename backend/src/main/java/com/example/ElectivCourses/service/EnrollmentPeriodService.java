package com.example.ElectivCourses.service;

import com.example.ElectivCourses.repository.EnrollmentPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface EnrollmentPeriodService {

    public void openEnrollmentPeriod();
    public void closeEnrollmentPeriod();
    public boolean isEnrollmentPeriodOpen();
}
