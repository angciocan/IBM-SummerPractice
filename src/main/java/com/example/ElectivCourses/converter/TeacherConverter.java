package com.example.ElectivCourses.converter;

import com.example.ElectivCourses.Model.dto.CourseDTO;
import com.example.ElectivCourses.Model.dto.TeacherDTO;
import com.example.ElectivCourses.Model.entity.Course;
import com.example.ElectivCourses.Model.entity.Teacher;

public class TeacherConverter {
    public static TeacherDTO toDTO(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(teacher.getId());
        teacherDTO.setName(teacher.getName());
        teacherDTO.setIs_admin(teacher.isAdmin());

        return teacherDTO;
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
