package com.example.electivecourses.model.dto;

import com.example.electivecourses.model.entity.EnrollmentStatus;

public class EnrollmentDTO {
    private Long id;
    private Long studentId;
    private Long courseId;
    private EnrollmentStatus status;

    public EnrollmentDTO() {}

    public EnrollmentDTO(Long id, Long studentId, Long courseId, EnrollmentStatus status) {
        this.id=id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.status=status;
    }

    public EnrollmentDTO(Long id, Long studentId, Long courseId) {
        this.id=id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }
}