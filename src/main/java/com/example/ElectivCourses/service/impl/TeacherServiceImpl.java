package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.dto.TeacherProfileDTO;
import com.example.ElectivCourses.converter.TeacherProfileConverter;
import com.example.ElectivCourses.repository.TeacherRepository;
import com.example.ElectivCourses.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public List<TeacherProfileDTO> getAllTeachers() {
        return teacherRepository.findAll().stream().map(TeacherProfileConverter::toDTO).collect(Collectors.toList());
    }
}
