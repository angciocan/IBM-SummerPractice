package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.model.dto.CourseDTO;
import com.example.ElectivCourses.model.entity.Course;
import com.example.ElectivCourses.model.entity.Enrollment;
import com.example.ElectivCourses.converter.CourseConverter;
import com.example.ElectivCourses.converter.TeacherConverter;
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
        return courseRepository.findAll().stream().map(CourseConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesByStudyYear(int studyYear) {
        return courseRepository.findByStudyYear(studyYear).stream().map(CourseConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesByMaxStudents(int maxStudents) {
        return courseRepository.findByMaxStudents(maxStudents).stream().map(CourseConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesByCategory(String category) {
        return courseRepository.findByCategory(category).stream().map(CourseConverter::toDTO).collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id) { return CourseConverter.toDTO(courseRepository.getReferenceById(id)); }

    @Override
    public void createCourse(Course courseToCreate){
        courseRepository.save(courseToCreate);
    }

    @Override
    public CourseDTO updateCourse(Long id, Course courseToUpdate){
        return CourseConverter.toDTO(courseRepository.findById(id).map(course -> {
            course.setCourseName(courseToUpdate.getCourseName());
            course.setMaxStudents(courseToUpdate.getMaxStudents());
            course.setStudyYear(courseToUpdate.getStudyYear());
            course.setCategory(courseToUpdate.getCategory());
            course.setDayOfWeek(courseToUpdate.getDayOfWeek());
            course.setTime(courseToUpdate.getTime());
            if (TeacherConverter.toDTO(courseToUpdate.getTeacher()) != null) {
                course.getTeacher().setName(TeacherConverter.toDTO(courseToUpdate.getTeacher()).getName());
            }

            return courseRepository.save(course);
        }).orElseThrow(() -> new RuntimeException("Cursul nu se poate actualiza")));
    }

    @Override
    public void deleteCourse(Long id){
        courseRepository.deleteById(id);
    }
    
}
