package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.dto.EnrollmentDTO;
import com.example.ElectivCourses.Model.dto.StudentDTO;
import com.example.ElectivCourses.Model.entity.Enrollment;
import com.example.ElectivCourses.Model.entity.Student;
import com.example.ElectivCourses.converter.EnrollmentConverter;
import com.example.ElectivCourses.converter.StudentConverter;
import com.example.ElectivCourses.repository.EnrollmentRepository;
import com.example.ElectivCourses.repository.StudentRepository;
import com.example.ElectivCourses.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(StudentConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public Student getStudentById(Long id) { return studentRepository.getReferenceById(id); }

    @Override
    public ArrayList<EnrollmentDTO> getStudentEnrollmentDTOs(long studentId) {
        List<Enrollment> studentEnrollments = getStudentById(studentId).getEnrollments();
        ArrayList<Enrollment> enrollmentList = new ArrayList<>(studentEnrollments);
        ArrayList<Enrollment> enrollmentListSorted = (ArrayList<Enrollment>) enrollmentList.stream()
                .sorted(Comparator.comparing(Enrollment::getId)).toList();

        return (ArrayList<EnrollmentDTO>) enrollmentListSorted.stream().map(EnrollmentConverter::toDTO).collect(Collectors.toList());

    }

    public ArrayList<Enrollment> getStudentEnrollments(long studentId) {
        List<Enrollment> studentEnrollments = getStudentById(studentId).getEnrollments();
        ArrayList<Enrollment> enrollmentList = new ArrayList<>(studentEnrollments);
        ArrayList<Enrollment> enrollmentListSorted = (ArrayList<Enrollment>) enrollmentList.stream()
                .sorted(Comparator.comparing(Enrollment::getId)).collect(Collectors.toList());

        return enrollmentListSorted;

    }

    @Override
    public void updateStudentPreferenceList(Long studentId, Long courseId, int nrInList) {
        Enrollment enrollment1 = enrollmentRepository.findAll().stream()
                .filter(enrollment -> (Objects.equals(enrollment.getStudent().getId(), studentId)) && (Objects.equals(enrollment.getCourse().getId(), courseId)))
                .findAny().get();

        Long enrollmentId1 = enrollment1.getId();

        Enrollment enrollment1Copy = new Enrollment();
        enrollment1Copy.setId(enrollment1.getId());
        enrollment1Copy.setStudent(enrollment1.getStudent());
        enrollment1Copy.setCourse(enrollment1.getCourse());
        enrollment1Copy.setStatus(enrollment1.getStatus());

        Enrollment enrollment2 = getStudentEnrollments(studentId).get(nrInList);


        Long enrollmentId2 = enrollment2.getId();

        enrollmentRepository.getReferenceById(enrollmentId1).setStudent(enrollment2.getStudent());
        enrollmentRepository.getReferenceById(enrollmentId1).setStatus(enrollment2.getStatus());
        enrollmentRepository.getReferenceById(enrollmentId1).setCourse(enrollment2.getCourse());

        enrollmentRepository.getReferenceById(enrollmentId2).setStudent(enrollment1Copy.getStudent());
        enrollmentRepository.getReferenceById(enrollmentId2).setStatus(enrollment1Copy.getStatus());
        enrollmentRepository.getReferenceById(enrollmentId2).setCourse(enrollment1Copy.getCourse());

        enrollmentRepository.save(enrollment1);
        enrollmentRepository.save(enrollment2);
    }
}


