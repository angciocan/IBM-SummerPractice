package com.example.electivecourses.controller;

import com.example.electivecourses.model.dto.TeacherDTO;
import com.example.electivecourses.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.electivecourses.model.dto.CourseDTO;
import com.example.electivecourses.model.entity.Course;
import com.example.electivecourses.service.CourseService;

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

    @PutMapping("/updateCourse")
    public CourseDTO updateCourse(@RequestParam Long id, @RequestBody Course courseToUpdate){
        return courseService.updateCourse(id, courseToUpdate);
    }

    @DeleteMapping("/deleteCourse")
    public void deleteCourse(@RequestParam Long id){
        courseService.deleteCourse(id);
    }
}