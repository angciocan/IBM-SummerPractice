package com.example.ElectivCourses.repository;
import com.example.ElectivCourses.model.dto.EnrollmentDTO;
import com.example.ElectivCourses.model.dto.StudentDTO;
import com.example.ElectivCourses.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT new com.example.ElectivCourses.model.dto.StudentDTO(s.id,s.name,s.facultySection,s.grade,s.studyYear) FROM Student s")
    List<StudentDTO> getAllStudents();

    @Query("SELECT new com.example.ElectivCourses.model.dto.EnrollmentDTO(e.id,e.student.id,e.course.id,e.status)" +
            " FROM Enrollment e" +
            " WHERE e.student.id = :studentId" +
            " ORDER BY e.id")
    ArrayList<EnrollmentDTO> findAllStudentsAsDTOByEnrollment(@Param("studentId") long studentId);


}
