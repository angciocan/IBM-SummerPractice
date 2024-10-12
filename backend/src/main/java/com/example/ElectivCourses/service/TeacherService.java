package com.example.ElectivCourses.service;

import com.example.ElectivCourses.model.dto.TeacherDTO;
import com.example.ElectivCourses.model.entity.Course;
import com.example.ElectivCourses.model.entity.Teacher;


import java.util.List;


public interface TeacherService {

    List<TeacherDTO> getAllTeachers();
    void createTeacher(Teacher teacher);
    void deleteTeacher(Long teacher_id);
    TeacherDTO updateTeacher(Long teacherId, Teacher teacherToUpdate);
}
