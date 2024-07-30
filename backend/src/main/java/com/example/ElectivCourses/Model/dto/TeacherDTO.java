package com.example.ElectivCourses.Model.dto;


public class TeacherDTO {
    private Long id;

    private String name;

    private boolean is_admin;

    public TeacherDTO(Long id, String name, boolean is_admin) {
        this.id = id;
        this.name = name;
        this.is_admin = is_admin;
    }

    public TeacherDTO() {}

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

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }
}
