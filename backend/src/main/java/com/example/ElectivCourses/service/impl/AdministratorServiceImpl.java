package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.model.dto.CourseDTO;
import com.example.ElectivCourses.model.dto.StudentDTO;
import com.example.ElectivCourses.model.dto.TeacherDTO;
import com.example.ElectivCourses.model.entity.Administrator;
import com.example.ElectivCourses.model.entity.Course;
import com.example.ElectivCourses.model.entity.Student;
import com.example.ElectivCourses.model.entity.Teacher;
import com.example.ElectivCourses.repository.AdministratorRepository;
import com.example.ElectivCourses.service.AdministratorService;
import com.example.ElectivCourses.service.CourseService;
import com.example.ElectivCourses.service.StudentService;
import com.example.ElectivCourses.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @Override
    public List<Administrator> getAllAdministrators() {
        return administratorRepository.findAll();
    }

    @Override
    public void createCourse(Course courseToCreate){
        courseService.createCourse(courseToCreate);
    }
    @Override
    public void deleteCourse(Long id){
        courseService.deleteCourse(id);
    }
    @Override
    public CourseDTO updateCourse(Long id, Course courseToUpdate){
        return courseService.updateCourse(id, courseToUpdate);
    }
    @Override
    public void createStudent(Student studentToCreate){
        studentService.createStudent(studentToCreate);
    }
    @Override
    public void deleteStudent(Long id){
        studentService.deleteStudent(id);
    }
    @Override
    public StudentDTO updateStudent(Long id, Student studentToUpdate){
        return studentService.updateStudent(id,studentToUpdate);
    }
    @Override
    public void createTeacher(Teacher teacherToCreate){
        teacherService.createTeacher(teacherToCreate);
    }
    @Override
    public void deleteTeacher(Long id){
        teacherService.deleteTeacher(id);
    }
    @Override
    public TeacherDTO updateTeacher(Long id, Teacher teacherToUpdate){
        return teacherService.updateTeacher(id,teacherToUpdate);
    }
}
