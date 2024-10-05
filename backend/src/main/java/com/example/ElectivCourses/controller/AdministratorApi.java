package com.example.ElectivCourses.controller;

import com.example.ElectivCourses.model.dto.CourseDTO;
import com.example.ElectivCourses.model.entity.Administrator;
import com.example.ElectivCourses.model.entity.Course;
import com.example.ElectivCourses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ElectivCourses.service.AdministratorService;

import java.util.List;

@RestController
@RequestMapping("/administrator")
@CrossOrigin(origins = "http://localhost:4200")
public class AdministratorApi {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    @ResponseBody
    public List<Administrator> getAllAdministrators() {
        return administratorService.getAllAdministrators();
    }

    @PostMapping("/create")
    public void createCourse(@RequestBody Course course) {
        courseService.createCourse(course);
    }
    @PostMapping("/update")
    public CourseDTO updateCourse(@RequestParam Long id, @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }
    @DeleteMapping("/delete")
    public void deleteCourse(@RequestParam Long id) {
        courseService.deleteCourse(id);
    }

}
