package com.example.ElectivCourses.converter;

import com.example.ElectivCourses.Model.dto.CourseDTO;
import com.example.ElectivCourses.Model.entity.Course;


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
