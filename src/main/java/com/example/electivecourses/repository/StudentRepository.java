package com.example.electivecourses.repository;
import com.example.electivecourses.model.dto.StudentDTO;
import com.example.electivecourses.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT new com.example.electivecourses.model.dto.StudentDTO(s.id,s.name,s.facultySection,s.grade,s.studyYear) FROM Student s")
    List<StudentDTO> findAllStudentsAsDTO();
}
