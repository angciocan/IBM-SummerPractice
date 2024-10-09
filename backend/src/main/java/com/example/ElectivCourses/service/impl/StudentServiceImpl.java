package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.converter.StudentConverter;
import com.example.ElectivCourses.model.dto.EnrollmentDTO;
import com.example.ElectivCourses.model.dto.StudentDTO;
import com.example.ElectivCourses.model.entity.Enrollment;
import com.example.ElectivCourses.model.entity.Student;
import com.example.ElectivCourses.repository.EnrollmentRepository;
import com.example.ElectivCourses.repository.StudentRepository;
import com.example.ElectivCourses.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAllStudentsAsDTO();
    }

    @Override
    public Student getStudentById(Long id) { return studentRepository.getReferenceById(id); }

    @Override
    public ArrayList<EnrollmentDTO> getStudentEnrollmentDTOs(long studentId) {
        return studentRepository.findAllStudentsAsDTOByEnrollment(studentId);
    }

    @Transactional
    @Override
    public void updateStudentPreferenceList(Long studentId, Long courseId, int nrInList) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        Enrollment enrollment1 = enrollments.stream()
                .filter(enrollment -> Objects.equals(enrollment.getCourse().getId(), courseId))
                .findFirst().orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Enrollment enrollment2 = enrollments.get(nrInList);

        Enrollment temp = new Enrollment();
        temp.setId(enrollment1.getId());
        temp.setStudent(enrollment1.getStudent());
        temp.setCourse(enrollment1.getCourse());
        temp.setStatus(enrollment1.getStatus());

        enrollment1.setStudent(enrollment2.getStudent());
        enrollment1.setCourse(enrollment2.getCourse());
        enrollment1.setStatus(enrollment2.getStatus());

        enrollment2.setStudent(temp.getStudent());
        enrollment2.setCourse(temp.getCourse());
        enrollment2.setStatus(temp.getStatus());

        enrollmentRepository.saveAll(Arrays.asList(enrollment1, enrollment2));
    }

    public List<Enrollment> getElectiveCourses(long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public void createStudent(Student student){
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId){
        studentRepository.deleteById(studentId);
    }

    public StudentDTO updateStudent(Long studentId, Student studentToUpdate){
        return StudentConverter.toDTO(studentRepository.findById(studentId).map(student -> {

            student.setName(studentToUpdate.getName());
            student.setFacultySection(studentToUpdate.getFacultySection());
            student.setStudyYear(studentToUpdate.getStudyYear());
            student.setGrade(studentToUpdate.getGrade());
            return studentRepository.save(student);

        }).orElseThrow(() -> new RuntimeException("Student not found")));
    }

}


