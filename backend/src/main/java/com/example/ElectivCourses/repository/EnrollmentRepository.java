package com.example.ElectivCourses.repository;

import com.example.ElectivCourses.model.dto.CourseDTO;
import com.example.ElectivCourses.model.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    List<Enrollment> findByCourseId(Long courseId);
    void deleteById(Long id);
    List<Enrollment> findByStudentId(Long studentId);
    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);


    @Query("SELECT new com.example.ElectivCourses.model.dto.CourseDTO(c.id,c.courseName,c.maxStudents,c.studyYear,c.category,c.dayOfWeek,c.time,c.teacher.id)" +
            " FROM Course c" +
            " JOIN Enrollment e ON c.id = e.course.id" +
            " JOIN Student s ON s.id = e.student.id  " +
            " WHERE s.id = :studentId")
    List<CourseDTO> getCoursesByStudentId(@Param("studentId") Long studentId);

    @Query("DELETE FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId")
    void deleteEnrollmentByStudentIdAndCourseId(@Param("studentId") long studentId, @Param("courseId") long courseId);



}
