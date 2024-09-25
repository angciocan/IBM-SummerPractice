package com.example.electivecourses.listener;

import com.example.electivecourses.event.EnrollmentPeriodClosedEvent;
import com.example.electivecourses.event.EnrollmentPeriodOpenedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentPeriodEventListener {

    @EventListener
    public void handleEnrollmentPeriodOpened(EnrollmentPeriodOpenedEvent event) {
        Long enrollmentPeriodId = event.getEnrollmentPeriodId();
        System.out.println("Enrollment period " + enrollmentPeriodId + " opened.");
    }

    @EventListener
    public void handleEnrollmentPeriodClosed(EnrollmentPeriodClosedEvent event) {
        Long enrollmentPeriodId = event.getEnrollmentPeriodId();
        System.out.println("Enrollment period " + enrollmentPeriodId + " closed.");
    }
}
