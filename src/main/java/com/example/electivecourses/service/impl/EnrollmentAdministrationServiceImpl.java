package com.example.electivecourses.service.impl;

import com.example.electivecourses.model.defaults.EnrollmentAdministrationDefaults;
import com.example.electivecourses.model.entity.EnrollmentAdministration;
import com.example.electivecourses.repository.EnrollmentAdministrationRepository;
import com.example.electivecourses.service.EnrollmentAdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EnrollmentAdministrationServiceImpl implements EnrollmentAdministrationService {
    @Autowired
    private EnrollmentAdministrationRepository enrollmentAdministrationRepository;
    @Autowired
    private EnrollmentAdministrationDefaults enrollmentAdministrationDefaults;


    @Override
    public List<EnrollmentAdministration> getAllEnrollmentAdministrations() {
        return enrollmentAdministrationRepository.findAll();
    }

    @Override
    public int nrOfMandatoryCoursesByYear(int studyYear) {
        EnrollmentAdministrationDefaults.DefaultCourses defaults = enrollmentAdministrationDefaults.getDefaultValuesByStudyYear(studyYear);
        if(defaults != null){
            return defaults.nrOfMandatoryCourses();
        }
        else throw new NoSuchElementException("No enrollment course found for study year " + studyYear);

    }

    @Override
    public int nrOfElectiveCoursesByYear(int studyYear) {
        EnrollmentAdministrationDefaults.DefaultCourses defaults = enrollmentAdministrationDefaults.getDefaultValuesByStudyYear(studyYear);
        if(defaults != null){
            return defaults.nrOfElectiveCourses();
        }
        else throw new NoSuchElementException("No enrollment course found for study year " + studyYear);

    }



}
