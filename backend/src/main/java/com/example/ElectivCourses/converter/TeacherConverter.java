package com.example.ElectivCourses.converter;

import com.example.ElectivCourses.model.dto.TeacherDTO;
import com.example.ElectivCourses.model.entity.Teacher;

public class TeacherConverter {
    public static TeacherDTO toDTO(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(teacher.getId());
        teacherDTO.setName(teacher.getName());

        return teacherDTO;
    }
}
