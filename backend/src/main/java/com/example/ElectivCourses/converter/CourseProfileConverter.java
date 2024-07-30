package com.example.ElectivCourses.converter;

import com.example.ElectivCourses.Model.dto.CourseDTO;
import com.example.ElectivCourses.Model.dto.CourseProfileDTO;
import com.example.ElectivCourses.Model.entity.Course;


public class CourseProfileConverter {

    public static CourseProfileDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }

        CourseProfileDTO courseProfileDTO = new CourseProfileDTO();
        courseProfileDTO.setId(course.getId());
        courseProfileDTO.setCourseName(course.getCourseName());
        courseProfileDTO.setMaxStudents(course.getMaxStudents());
        courseProfileDTO.setStudyYear(course.getStudyYear());
        courseProfileDTO.setCategory(course.getCategory());
        courseProfileDTO.setDayOfWeek(course.getDayOfWeek());
        courseProfileDTO.setTime(course.getTime());

        return courseProfileDTO;
    }

//    public Enrollment toEntity(EnrollmentDTO enrollmentDTO) {
//        if (enrollmentDTO == null) {
//            return null;
//        }
//
//        CourseDTO courseDTO = new CourseDTO();
//
//        enrollment.setStudent(studentService.getStudentById(enrollmentDTO.getStudentId()));
//
//        enrollment.setCourse(courseService.getCourseById(enrollmentDTO.getCourseId()));
//
//        enrollment.setStatus(EnrollmentStatus.PENDING);
//
//        return enrollment;
//
//    }
}

//private Long id;
//
//private String courseName;
//
//private int maxStudents;
//
//private int studyYear;
//
//private String category;
//
//private DayOfWeek dayOfWeek;
//
//private LocalTime time;
//
//private Teacher teacher;
