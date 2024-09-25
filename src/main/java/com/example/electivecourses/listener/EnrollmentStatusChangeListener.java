package com.example.electivecourses.listener;

import com.example.electivecourses.event.EnrollmentStatusChangedEvent;
import org.springframework.context.event.EventListener;

public class EnrollmentStatusChangeListener {

    @EventListener
    public void handleEnrollmentStatusChangedEvent(EnrollmentStatusChangedEvent event) {
        System.out.println("Enrollment ID " + event.getEnrollmentId() + " status changed to " + event.getEnrollmentStatus());
    }
}
