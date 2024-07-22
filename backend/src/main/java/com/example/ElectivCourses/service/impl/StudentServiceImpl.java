package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.entity.Student;
import com.example.ElectivCourses.repository.StudentRepository;
import com.example.ElectivCourses.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
