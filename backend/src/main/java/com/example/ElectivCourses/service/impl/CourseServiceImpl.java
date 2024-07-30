package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.dto.CourseDTO;
import com.example.ElectivCourses.Model.entity.Course;
import com.example.ElectivCourses.Model.entity.Enrollment;
import com.example.ElectivCourses.converter.CourseConverter;
import com.example.ElectivCourses.repository.CourseRepository;
import com.example.ElectivCourses.repository.EnrollmentRepository;
import com.example.ElectivCourses.service.CourseService;
import com.example.ElectivCourses.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentService studentService;

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(course -> CourseConverter.toDTO(course)).collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesByStudyYear(int studyYear) {
        return courseRepository.findByStudyYear(studyYear).stream().map(course -> CourseConverter.toDTO(course)).collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesByMaxStudents(int maxStudents) {
        return courseRepository.findByMaxStudents(maxStudents).stream().map(course -> CourseConverter.toDTO(course)).collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesByCategory(String category) {
        return courseRepository.findByCategory(category).stream().map(course -> CourseConverter.toDTO(course)).collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id) { return CourseConverter.toDTO(courseRepository.getReferenceById(id)); }

    @Override
    public List<CourseDTO> getCoursesByStudentIdApplications(Long studentId) {
        return enrollmentRepository.findAll().stream()
                .filter(enrollment -> enrollment.getStudent() == studentService.getStudentById(studentId))
                .map(Enrollment::getCourse).map(CourseConverter::toDTO).collect(Collectors.toList());
    }
    
}
