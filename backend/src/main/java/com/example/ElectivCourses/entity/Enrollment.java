package com.example.ElectivCourses.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
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
    private LocalDate start_time;

    @Column
    private LocalDate end_time;

    @Column
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    // Getters and setters
}