package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.dto.StudentDTO;
import com.example.ElectivCourses.Model.entity.Student;
import com.example.ElectivCourses.converter.StudentConverter;
import com.example.ElectivCourses.repository.StudentRepository;
import com.example.ElectivCourses.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(StudentConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public Student getStudentById(Long id) { return studentRepository.getReferenceById(id); }

}
