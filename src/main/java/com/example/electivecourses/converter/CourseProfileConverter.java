package com.example.electivecourses.converter;

import com.example.electivecourses.model.dto.CourseProfileDTO;
import com.example.electivecourses.model.entity.Course;


public class CourseProfileConverter {

    public static CourseProfileDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }

        CourseProfileDTO courseProfileDTO = new CourseProfileDTO();
        courseProfileDTO.setId(course.getId());
        courseProfileDTO.setCourseName(course.getCourseName());
        courseProfileDTO.setMaxStudents(course.getMaxStudents());
        courseProfileDTO.setStudyYear(course.getStudyYear());
        courseProfileDTO.setCategory(course.getCategory());
        courseProfileDTO.setDayOfWeek(course.getDayOfWeek());
        courseProfileDTO.setTime(course.getTime());

        return courseProfileDTO;
    }

}

