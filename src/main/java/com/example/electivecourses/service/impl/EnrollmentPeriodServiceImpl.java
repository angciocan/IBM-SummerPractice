package com.example.electivecourses.service.impl;

import com.example.electivecourses.model.entity.EnrollmentPeriod;
import com.example.electivecourses.repository.EnrollmentPeriodRepository;
import com.example.electivecourses.service.EnrollmentPeriodService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentPeriodServiceImpl implements EnrollmentPeriodService {

    @Autowired
    private EnrollmentPeriodRepository enrollmentPeriodRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void openEnrollmentPeriod() {


        rabbitTemplate.convertAndSend("enrollment_exchange","enrollment-routing-key","Opened the enrollment period");

        EnrollmentPeriod period = enrollmentPeriodRepository.findOrCreateSingleton();
        period.setOpen(true);
        enrollmentPeriodRepository.save(period);


    }

    @Override
    public void closeEnrollmentPeriod() {

        rabbitTemplate.convertAndSend("enrollment_exchange","enrollment-routing-key","Closed the enrollment period");


        EnrollmentPeriod period = enrollmentPeriodRepository.findOrCreateSingleton();
        period.setOpen(false);
        enrollmentPeriodRepository.save(period);

    }

    @Override
    public boolean isEnrollmentPeriodOpen() {
        return enrollmentPeriodRepository.findOrCreateSingleton().isOpen();
    }

}
