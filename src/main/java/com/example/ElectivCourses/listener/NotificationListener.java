package com.example.ElectivCourses.listener;

import com.example.ElectivCourses.event.StudentEnrolledEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @EventListener
    public void handleStudentEnrolledEvent(StudentEnrolledEvent event) {
        System.out.println("Student ID " + event.getStudentId() + " enrolled in Course ID " + event.getCourseId());
    }
}
