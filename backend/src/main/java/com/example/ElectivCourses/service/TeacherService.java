package com.example.ElectivCourses.service;

import com.example.ElectivCourses.Model.dto.TeacherProfileDTO;
import com.example.ElectivCourses.Model.entity.Teacher;


import java.util.List;


public interface TeacherService {
    List<TeacherProfileDTO> getAllTeachers();
}
