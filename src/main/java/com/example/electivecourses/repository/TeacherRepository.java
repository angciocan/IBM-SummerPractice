package com.example.electivecourses.repository;

import com.example.electivecourses.model.dto.TeacherDTO;
import com.example.electivecourses.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    @Query("SELECT new com.example.electivecourses.model.dto.TeacherDTO(t.id,t.name) FROM Teacher t ")
    List<TeacherDTO> getAllTeachers();
}