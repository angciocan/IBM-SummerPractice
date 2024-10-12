package com.example.ElectivCourses.controller;

import com.example.ElectivCourses.model.dto.TeacherDTO;
import com.example.ElectivCourses.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ElectivCourses.model.dto.CourseDTO;
import com.example.ElectivCourses.model.entity.Course;
import com.example.ElectivCourses.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherApi {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/")
    @ResponseBody
    public List<TeacherDTO> getAllTeachers() {
        return  teacherService.getAllTeachers();
    }

}