package com.example.electivecourses.service;

import com.example.electivecourses.model.dto.EnrollmentDTO;
import com.example.electivecourses.model.dto.StudentDTO;
import com.example.electivecourses.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    Student getStudentById(Long id);
    ArrayList<EnrollmentDTO> getStudentEnrollmentDTOs(long studentId);
    void updateStudentPreferenceList(Long studentId, Long courseId, int nrInList);

}
