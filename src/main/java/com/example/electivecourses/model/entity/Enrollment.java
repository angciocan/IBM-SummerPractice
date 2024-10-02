package com.example.electivecourses.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    @Column
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    public Enrollment() {
    }

    public Enrollment(Student student, Course course, EnrollmentStatus status) {
        this.student = student;
        this.course = course;
        this.status = status;
    }

    public Enrollment(Long id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}