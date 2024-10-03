package com.example.electivecourses.converter;

import com.example.electivecourses.model.dto.CourseProfileDTO;
import com.example.electivecourses.model.dto.TeacherProfileDTO;
import com.example.electivecourses.model.entity.Course;
import com.example.electivecourses.model.entity.Teacher;

public class TeacherProfileConverter {
    public static TeacherProfileDTO toDTO(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherProfileDTO teacherProfileDTO = new TeacherProfileDTO();
        teacherProfileDTO.setId(teacher.getId());
        teacherProfileDTO.setName(teacher.getName());

        for (Course course : teacher.getCourses()) {
            CourseProfileDTO courseProfileDTO = CourseProfileConverter.toDTO(course);
            teacherProfileDTO.getCourses().add(courseProfileDTO);
        }

        return teacherProfileDTO;
    }
}
