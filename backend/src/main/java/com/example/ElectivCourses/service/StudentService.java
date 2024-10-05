package com.example.ElectivCourses.service;

import com.example.ElectivCourses.model.dto.EnrollmentDTO;
import com.example.ElectivCourses.model.dto.StudentDTO;
import com.example.ElectivCourses.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    Student getStudentById(Long id);
    ArrayList<EnrollmentDTO> getStudentEnrollmentDTOs(long studentId);
    void updateStudentPreferenceList(Long studentId, Long courseId, int nrInList);

    void createStudent(Student studentToCreate);

    void deleteStudent(Long id);

    StudentDTO updateStudent(Long id, Student studentToUpdate);
}
