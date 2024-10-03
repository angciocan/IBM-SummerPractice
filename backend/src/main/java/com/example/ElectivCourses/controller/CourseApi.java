package com.example.ElectivCourses.controller;

import com.example.ElectivCourses.model.dto.CourseDTO;
import com.example.ElectivCourses.model.entity.Course;
import com.example.ElectivCourses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseApi {
    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    @ResponseBody
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/by-study-year")
    public List<CourseDTO> getAllCoursesByStudyYear(@RequestParam int studyYear) { return courseService.getCoursesByStudyYear(studyYear); }

    @GetMapping("/by-max-students")
    public List<CourseDTO> getAllCoursesByMaxStudents(@RequestParam int maxStudents) { return courseService.getCoursesByMaxStudents(maxStudents); }

    @GetMapping("/by-category")
    public List<CourseDTO> getAllCoursesByCategory(@RequestParam String category) { return courseService.getCoursesByCategory(category); }

    @GetMapping("/by-id")
    public CourseDTO getCourseById(@RequestParam Long id) {return courseService.getCourseById(id);}

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