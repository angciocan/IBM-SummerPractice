package com.example.ElectivCourses.Model.entity;

import com.example.ElectivCourses.Model.dto.EnrollmentDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
//    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column
    private String courseName;

    @Column
    private int maxStudents;

    @Column
    private int studyYear;

    @Column
    private String category;

    @Column
    private DayOfWeek dayOfWeek;

    @Column
    private LocalTime time;

    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollments;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Course(String courseName, int maxStudents, int studyYear, String category, DayOfWeek dayOfWeek, LocalTime time) {
        this.courseName = courseName;
        this.maxStudents = maxStudents;
        this.studyYear = studyYear;
        this.category = category;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
    }

    public Course() {}

    public Long getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
