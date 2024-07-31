package com.example.ElectivCourses.Model.dto;

import jakarta.persistence.Column;

public class StudentDTO {
    private Long id;

    private String name;

    private String facultySection;

    private int studyYear;

    private int grade;

    public StudentDTO(Long id, String name, String facultySection, int grade, int studyYear) {
        this.id = id;
        this.name = name;
        this.facultySection = facultySection;
        this.grade = grade;
        this.studyYear = studyYear;
    }

    public StudentDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
