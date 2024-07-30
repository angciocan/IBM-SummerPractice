package com.example.ElectivCourses.converter;

import com.example.ElectivCourses.Model.dto.CourseDTO;
import com.example.ElectivCourses.Model.entity.Course;


public class CourseConverter {

    public static CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setCourseName(course.getCourseName());
        courseDTO.setMaxStudents(course.getMaxStudents());
        courseDTO.setStudyYear(course.getStudyYear());
        courseDTO.setCategory(course.getCategory());
        courseDTO.setDayOfWeek(course.getDayOfWeek());
        courseDTO.setTime(course.getTime());
        courseDTO.setTeacherDTO(TeacherConverter.toDTO(course.getTeacher()));

        return courseDTO;
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
