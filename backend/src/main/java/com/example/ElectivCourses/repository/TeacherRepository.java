package com.example.ElectivCourses.repository;

import com.example.ElectivCourses.model.dto.TeacherDTO;
import com.example.ElectivCourses.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    @Query("SELECT new com.example.ElectivCourses.model.dto.TeacherDTO(t.id,t.name) FROM Teacher t ")
    List<TeacherDTO> getAllTeachers();
}