package com.example.ElectivCourses.converter;

import com.example.ElectivCourses.Model.dto.StudentDTO;
import com.example.ElectivCourses.Model.dto.TeacherDTO;
import com.example.ElectivCourses.Model.entity.Student;
import com.example.ElectivCourses.Model.entity.Teacher;

public class StudentConverter {
    public static StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setFacultySection(student.getFacultySection());
        studentDTO.setStudyYear(student.getStudyYear());
        studentDTO.setGrade(student.getGrade());
        studentDTO.setAdmin(student.isAdmin());

        return studentDTO;
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
