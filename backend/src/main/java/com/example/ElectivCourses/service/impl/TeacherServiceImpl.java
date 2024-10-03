package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.model.dto.TeacherDTO;
import com.example.ElectivCourses.repository.TeacherRepository;
import com.example.ElectivCourses.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public List<TeacherDTO> getAllTeachers() {
       return teacherRepository.getAllTeachers();
    }
}
