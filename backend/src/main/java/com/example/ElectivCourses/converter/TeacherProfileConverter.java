package com.example.ElectivCourses.converter;

import com.example.ElectivCourses.Model.dto.CourseProfileDTO;
import com.example.ElectivCourses.Model.dto.TeacherProfileDTO;
import com.example.ElectivCourses.Model.entity.Course;
import com.example.ElectivCourses.Model.entity.Teacher;

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
