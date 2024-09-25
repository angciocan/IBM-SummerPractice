package com.example.electivecourses.controller;

import com.example.electivecourses.model.dto.EnrollmentDTO;
import com.example.electivecourses.model.dto.StudentDTO;
import com.example.electivecourses.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentApi {

    @Autowired
    private StudentService studentService;

    @GetMapping("/getStudent")
    @ResponseBody
    public List<StudentDTO> getAllStudents() {
        return  studentService.getAllStudents();
    }

    @GetMapping("/getStudentEnrollments/{studentId}")
    public ArrayList<EnrollmentDTO> getAllStudentEnrollments(@PathVariable("studentId") Long studentId) {
        return studentService.getStudentEnrollmentDTOs(studentId);
    }

    @PatchMapping("/updateStudent/reorderPriorityList/{studentId}/{courseId}/{nrInList}")
    public void updateStudentPreferenceList(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId, @PathVariable("nrInList") int nrInList) {
        studentService.updateStudentPreferenceList(studentId, courseId, nrInList);
    }
}
