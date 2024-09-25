package com.example.electivecourses.service.impl;

import com.example.electivecourses.model.entity.EnrollmentPeriod;
import com.example.electivecourses.event.EnrollmentPeriodClosedEvent;
import com.example.electivecourses.event.EnrollmentPeriodOpenedEvent;
import com.example.electivecourses.repository.EnrollmentPeriodRepository;
import com.example.electivecourses.service.EnrollmentPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentPeriodServiceImpl implements EnrollmentPeriodService {

    @Autowired
    private EnrollmentPeriodRepository enrollmentPeriodRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void openEnrollmentPeriod() {
        EnrollmentPeriod period = enrollmentPeriodRepository.findOrCreateSingleton();
        period.setOpen(true);
        enrollmentPeriodRepository.save(period);

        eventPublisher.publishEvent(new EnrollmentPeriodOpenedEvent(this, period.getId()));

    }

    @Override
    public void closeEnrollmentPeriod() {
        EnrollmentPeriod period = enrollmentPeriodRepository.findOrCreateSingleton();
        period.setOpen(false);
        enrollmentPeriodRepository.save(period);

        eventPublisher.publishEvent(new EnrollmentPeriodClosedEvent(this, period.getId()));
    }

    @Override
    public boolean isEnrollmentPeriodOpen() {
        return enrollmentPeriodRepository.findOrCreateSingleton().isOpen();
    }

}
