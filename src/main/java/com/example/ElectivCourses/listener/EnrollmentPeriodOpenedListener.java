package com.example.ElectivCourses.listener;

import com.example.ElectivCourses.event.EnrollmentPeriodOpenedEvent;
import org.springframework.context.event.EventListener;

public class EnrollmentPeriodOpenedListener {

    @EventListener
    public void handEnrollmentPeriodOpened(EnrollmentPeriodOpenedEvent event) {
        System.out.println("Enrollment period opened with ID: " + event.getEnrollmentPeriodId());
    }
}
