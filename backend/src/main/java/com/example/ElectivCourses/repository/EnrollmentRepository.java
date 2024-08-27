package com.example.ElectivCourses.repository;

import com.example.ElectivCourses.Model.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    void deleteById(Long id);
    List<Enrollment> findByStudentId(Long studentId);
    boolean existsByCourseIdAndStudentId(@Param("courseId") Long courseId, @Param("studentId") Long studentId);
}
