package com.example.ElectivCourses.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column
    private String course_name;

    @Column
    private int max_students;

    @Column
    private int year_of_study;

    @Column
    private String category;

    @Column
    private DayOfWeek day_of_week;

    @Column
    private LocalTime time;

    public Course(String course_name, int max_students, int year_of_study, String category, DayOfWeek day_of_week, LocalTime time) {
        this.course_name = course_name;
        this.max_students = max_students;
        this.year_of_study = year_of_study;
        this.category = category;
        this.day_of_week = day_of_week;
        this.time = time;
    }

    public Course() {}

    public Long getId() {
        return id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getMax_students() {
        return max_students;
    }

    public void setMax_students(int max_students) {
        this.max_students = max_students;
    }

    public int getYear_of_study() {
        return year_of_study;
    }

    public void setYear_of_study(int year_of_study) {
        this.year_of_study = year_of_study;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public DayOfWeek getDayOfWeek() {
        return day_of_week;
    }

    public void setDayOfWeek(DayOfWeek day_of_week) {
        this.day_of_week = day_of_week;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
