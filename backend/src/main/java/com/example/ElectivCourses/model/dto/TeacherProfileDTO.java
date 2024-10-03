package com.example.ElectivCourses.model.dto;

import java.util.ArrayList;
import java.util.List;

public class TeacherProfileDTO {
    private Long id;

    private String name;

    private List<CourseProfileDTO> courses = new ArrayList<>();


    public TeacherProfileDTO() {}

    public Long getId() {
        return id;
    }

    public TeacherProfileDTO(Long id, String name, List<CourseProfileDTO> courses) {
        this.id = id;
        this.name = name;
        this.courses = courses;
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

    public List<CourseProfileDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseProfileDTO> courses) {
        this.courses = courses;
    }
}

