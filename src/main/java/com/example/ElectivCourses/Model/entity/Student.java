package com.example.ElectivCourses.Model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
//    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column
    private String name;

    @Column
    private String facultySection;

    @Column
    private int studyYear;

    @Column
    private int grade;

    @Column
    private boolean admin;

    @OneToMany(mappedBy = "student")
    private Set<Enrollment> enrollments;

    public Student(String name, String facultySection, int studyYear, int grade, boolean admin) {
        this.name = name;
        this.facultySection = facultySection;
        this.studyYear = studyYear;
        this.grade = grade;
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Student() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacultySection() {
        return facultySection;
    }

    public void setFacultySection(String facultySection) {
        this.facultySection = facultySection;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setId(Long id) {
        this.id = id;
    }

}