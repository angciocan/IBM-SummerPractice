package com.example.electivecourses.converter;

import com.example.electivecourses.model.dto.TeacherDTO;
import com.example.electivecourses.model.entity.Teacher;

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
