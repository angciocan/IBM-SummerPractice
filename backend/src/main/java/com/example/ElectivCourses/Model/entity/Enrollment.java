package com.example.ElectivCourses.Model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

//    public static LocalDate getStart_time() {
//        return start_time;
//    }
//
//    public static void setStart_time(LocalDate start_time) {
//        Enrollment.start_time = start_time;
//    }
//
//    public static LocalDate getEnd_time() {
//        return end_time;
//    }
//
//    public static void setEnd_time(LocalDate end_time) {
//        Enrollment.end_time = end_time;
//    }
}