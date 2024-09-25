package com.example.electivecourses.service;

import com.example.electivecourses.model.dto.TeacherProfileDTO;


import java.util.List;


public interface TeacherService {
    List<TeacherProfileDTO> getAllTeachers();
}
