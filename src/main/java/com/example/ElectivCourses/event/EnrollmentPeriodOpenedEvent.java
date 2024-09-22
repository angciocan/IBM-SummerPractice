package com.example.ElectivCourses.event;

import org.springframework.context.ApplicationEvent;

public class EnrollmentPeriodOpenedEvent extends ApplicationEvent {

    private final Long enrollmentPeriodId;

    public EnrollmentPeriodOpenedEvent(Object source, Long enrollmentPeriodId) {
        super(source);
        this.enrollmentPeriodId = enrollmentPeriodId;
    }

    public Long getEnrollmentPeriodId() {
        return enrollmentPeriodId;
    }


}