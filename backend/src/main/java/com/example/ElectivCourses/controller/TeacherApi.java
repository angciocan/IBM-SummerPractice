package com.example.ElectivCourses.controller;

import com.example.ElectivCourses.Model.entity.Teacher;
import com.example.ElectivCourses.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherApi {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/")
    @ResponseBody
    public List<Teacher> getAllTeachers() {
        return  teacherService.getAllTeachers();
    }

}