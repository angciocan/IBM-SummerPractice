package com.example.electivecourses.event;

import org.springframework.context.ApplicationEvent;

public class EnrollmentPeriodClosedEvent extends ApplicationEvent {

    private final Long enrollmentPeriodId;
    public EnrollmentPeriodClosedEvent(Object source, Long enrollmentPeriodId) {
        super(source);
        this.enrollmentPeriodId = enrollmentPeriodId;
    }
    public Long getEnrollmentPeriodId() {
        return enrollmentPeriodId;
    }
}
