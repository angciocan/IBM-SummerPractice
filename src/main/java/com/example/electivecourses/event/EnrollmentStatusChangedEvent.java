package com.example.electivecourses.event;

import com.example.electivecourses.model.entity.EnrollmentStatus;
import org.springframework.context.ApplicationEvent;

public class EnrollmentStatusChangedEvent extends ApplicationEvent {
    private final Long enrollmentId;
    private final EnrollmentStatus enrollmentStatus;

    public EnrollmentStatusChangedEvent(Object source, Long enrollmentId, EnrollmentStatus enrollmentStatus) {
        super(source);
        this.enrollmentId = enrollmentId;
        this.enrollmentStatus = enrollmentStatus;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }
}
