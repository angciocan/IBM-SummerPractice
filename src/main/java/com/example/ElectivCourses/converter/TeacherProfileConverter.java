package com.example.ElectivCourses.converter;

import com.example.ElectivCourses.Model.dto.CourseDTO;
import com.example.ElectivCourses.Model.dto.CourseProfileDTO;
import com.example.ElectivCourses.Model.dto.TeacherDTO;
import com.example.ElectivCourses.Model.dto.TeacherProfileDTO;
import com.example.ElectivCourses.Model.entity.Course;
import com.example.ElectivCourses.Model.entity.Teacher;

public class TeacherProfileConverter {
    public static TeacherProfileDTO toDTO(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherProfileDTO teacherProfileDTO = new TeacherProfileDTO();
        teacherProfileDTO.setId(teacher.getId());
        teacherProfileDTO.setName(teacher.getName());

        for (Course course : teacher.getCourses()) {
            CourseProfileDTO courseProfileDTO = CourseProfileConverter.toDTO(course);
            teacherProfileDTO.getCourses().add(courseProfileDTO);
        }

        return teacherProfileDTO;
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
