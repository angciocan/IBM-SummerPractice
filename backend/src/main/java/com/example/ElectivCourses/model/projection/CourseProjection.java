package com.example.ElectivCourses.model.projection;

import com.example.ElectivCourses.model.dto.TeacherDTO;

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
