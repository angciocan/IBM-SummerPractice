package com.example.ElectivCourses.controller;

import com.example.ElectivCourses.Model.dto.TeacherProfileDTO;
import com.example.ElectivCourses.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ElectivCourses.Model.dto.CourseDTO;
import com.example.ElectivCourses.Model.entity.Course;
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
    public List<TeacherProfileDTO> getAllTeachers() {
        return  teacherService.getAllTeachers();
    }

    @PostMapping("/createCourse")
    public CourseDTO createCourse(@RequestBody Course courseToCreate){
        return courseService.createCourse(courseToCreate);
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