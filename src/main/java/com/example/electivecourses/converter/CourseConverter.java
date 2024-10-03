package com.example.electivecourses.converter;

import com.example.electivecourses.model.dto.CourseDTO;
import com.example.electivecourses.model.dto.TeacherDTO;
import com.example.electivecourses.model.entity.Course;
import com.example.electivecourses.model.projection.CourseProjection;

import java.time.DayOfWeek;
import java.time.LocalTime;


public class CourseConverter {

    public static CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setCourseName(course.getCourseName());
        courseDTO.setMaxStudents(course.getMaxStudents());
        courseDTO.setStudyYear(course.getStudyYear());
        courseDTO.setCategory(course.getCategory());
        courseDTO.setDayOfWeek(course.getDayOfWeek());
        courseDTO.setTime(course.getTime());
        courseDTO.setTeacherDTO(TeacherConverter.toDTO(course.getTeacher()));

        return courseDTO;
    }


}
