package com.example.ElectivCourses.service;

import com.example.ElectivCourses.Model.dto.EnrollmentDTO;
import com.example.ElectivCourses.Model.dto.StudentDTO;
import com.example.ElectivCourses.Model.entity.Student;

import java.util.ArrayList;
import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    Student getStudentById(Long id);
    ArrayList<EnrollmentDTO> getStudentEnrollmentDTOs(long studentId);
    void updateStudentPreferenceList(Long studentId, Long courseId, int nrInList);

}
