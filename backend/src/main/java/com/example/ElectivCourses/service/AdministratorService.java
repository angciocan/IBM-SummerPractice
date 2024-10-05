package com.example.ElectivCourses.service;

import com.example.ElectivCourses.model.dto.CourseDTO;
import com.example.ElectivCourses.model.dto.StudentDTO;
import com.example.ElectivCourses.model.dto.TeacherDTO;
import com.example.ElectivCourses.model.entity.Administrator;
import com.example.ElectivCourses.model.entity.Course;
import com.example.ElectivCourses.model.entity.Student;
import com.example.ElectivCourses.model.entity.Teacher;

import java.util.List;

public interface AdministratorService {
    List<Administrator> getAllAdministrators();

    void createCourse(Course course);

    CourseDTO updateCourse(Long id, Course course);

    void deleteCourse(Long id);

    void createStudent(Student studentToCreate);

    void deleteStudent(Long id);

    StudentDTO updateStudent(Long id, Student studentToUpdate);

    void createTeacher(Teacher teacherToCreate);

    void deleteTeacher(Long id);

    TeacherDTO updateTeacher(Long id, Teacher teacherToUpdate);
}
