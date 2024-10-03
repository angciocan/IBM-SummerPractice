package com.example.ElectivCourses.converter;

import com.example.ElectivCourses.model.dto.StudentDTO;
import com.example.ElectivCourses.model.entity.Student;

public class StudentConverter {
    public static StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setFacultySection(student.getFacultySection());
        studentDTO.setStudyYear(student.getStudyYear());
        studentDTO.setGrade(student.getGrade());

        return studentDTO;
    }

}
