package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.entity.Teacher;
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
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
}
