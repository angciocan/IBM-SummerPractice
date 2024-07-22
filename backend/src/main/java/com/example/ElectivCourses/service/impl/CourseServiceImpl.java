package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.entity.Course;
import com.example.ElectivCourses.repository.CourseRepository;
import com.example.ElectivCourses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
