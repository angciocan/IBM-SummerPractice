package com.example.ElectivCourses.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column
    private String name;

    @Column
    private String faculty_section;

    @Column
    private int study_year;

    @Column
    private int grade;

    @Column
    private boolean is_admin;

    public Student(String name, String faculty_section, int study_year, int grade, boolean is_admin) {
        this.name = name;
        this.faculty_section = faculty_section;
        this.study_year = study_year;
        this.grade = grade;
        this.is_admin = is_admin;
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

    public String getFaculty_section() {
        return faculty_section;
    }

    public void setFaculty_section(String faculty_section) {
        this.faculty_section = faculty_section;
    }

    public int getStudy_year() {
        return study_year;
    }

    public void setStudy_year(int study_year) {
        this.study_year = study_year;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }
}