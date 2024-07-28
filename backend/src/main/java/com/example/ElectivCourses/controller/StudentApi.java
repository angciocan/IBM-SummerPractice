package com.example.ElectivCourses.controller;

import com.example.ElectivCourses.entity.Student;
import com.example.ElectivCourses.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentApi {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    @ResponseBody
    public List<Student> getAllStudents() {
        return  studentService.getAllStudents();
    }

}
