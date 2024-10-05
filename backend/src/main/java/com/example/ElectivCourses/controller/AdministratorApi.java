package com.example.ElectivCourses.controller;

import com.example.ElectivCourses.model.dto.CourseDTO;
import com.example.ElectivCourses.model.dto.StudentDTO;
import com.example.ElectivCourses.model.dto.TeacherDTO;
import com.example.ElectivCourses.model.entity.Administrator;
import com.example.ElectivCourses.model.entity.Course;
import com.example.ElectivCourses.model.entity.Student;
import com.example.ElectivCourses.model.entity.Teacher;
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

    @GetMapping("/")
    @ResponseBody
    public List<Administrator> getAllAdministrators() {
        return administratorService.getAllAdministrators();
    }

    @PostMapping("/createCourse")
    public void createCourse(@RequestBody Course course) {
        administratorService.createCourse(course);
    }

    @PostMapping("/updateCourse")
    public CourseDTO updateCourse(@RequestParam Long id, @RequestBody Course course) {
        return administratorService.updateCourse(id, course);
    }

    @DeleteMapping("/deleteCourse")
    public void deleteCourse(@RequestParam Long id) {
        administratorService.deleteCourse(id);
    }

    @PostMapping("/createStudent")
    public void createStudent(@RequestBody Student student) {
        administratorService.createStudent(student);
    }

    @PostMapping("/updateStudent")
    public StudentDTO updateStudent(@RequestParam Long id, @RequestBody Student student) {
        return administratorService.updateStudent(id, student);
    }

    @DeleteMapping("/deleteStudent")
    public void deleteStudent(@RequestParam Long id) {
        administratorService.deleteStudent(id);
    }

    @PostMapping("/createTeacher")
    public void createTeacher(@RequestBody Teacher teacher) {
        administratorService.createTeacher(teacher);
    }
    @PostMapping("/updateTeacher")
    public TeacherDTO updateTeacher(@RequestParam Long id, @RequestBody Teacher teacher) {
        return administratorService.updateTeacher(id, teacher);
    }

    @DeleteMapping("/deleteTeacher")
    public void deleteTeacher(@RequestParam Long id) {
        administratorService.deleteTeacher(id);
    }

}



