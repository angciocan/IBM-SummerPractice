package com.example.electivecourses.service.impl;

import com.example.electivecourses.model.dto.TeacherDTO;
import com.example.electivecourses.repository.TeacherRepository;
import com.example.electivecourses.service.TeacherService;
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
