package com.example.ElectivCourses.controller;

import com.example.ElectivCourses.entity.Course;
import com.example.ElectivCourses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseApi {
    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    @ResponseBody
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
}
