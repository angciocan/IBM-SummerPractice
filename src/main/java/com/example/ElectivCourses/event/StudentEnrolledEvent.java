package com.example.ElectivCourses.event;

import org.springframework.context.ApplicationEvent;

public class StudentEnrolledEvent extends ApplicationEvent {
    public final Long studentId;
    public final Long courseId;
    public StudentEnrolledEvent(Object source, Long studentId, Long courseId) {
        super(source);
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getCourseId() {
        return courseId;
    }
}
