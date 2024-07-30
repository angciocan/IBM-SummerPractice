package com.example.ElectivCourses.Model.dto;

public class EnrollmentDTO {
    private Long studentId;
    private Long courseId;

    public EnrollmentDTO() {}

    public EnrollmentDTO(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}