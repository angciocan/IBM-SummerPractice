package com.example.ElectivCourses.repository;
import com.example.ElectivCourses.Model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
