package com.example.electivecourses.model.projection;

import com.example.electivecourses.model.dto.TeacherDTO;

import java.time.DayOfWeek;
import java.time.LocalTime;

public interface CourseProjection {
    String getCourseName();
    int getMaxStudents();
    int getStudyYear();
    String getCategory();
    DayOfWeek getDayOfWeek();
    LocalTime getTime();
    TeacherDTO getTeacherDTO();
    Long getId();
}
