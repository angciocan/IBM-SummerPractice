package com.example.electivecourses.repository;

import com.example.electivecourses.model.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    List<Enrollment> findByCourseId(Long courseId);
    void deleteById(Long id);
    List<Enrollment> findByStudentId(Long studentId);
    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);


    @Query("DELETE FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId")
    void deleteEnrollmentByStudentIdAndCourseId(@Param("studentId") long studentId, @Param("courseId") long courseId);
}
