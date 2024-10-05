package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.converter.TeacherConverter;
import com.example.ElectivCourses.model.dto.TeacherDTO;
import com.example.ElectivCourses.model.entity.Teacher;
import com.example.ElectivCourses.repository.TeacherRepository;
import com.example.ElectivCourses.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public List<TeacherDTO> getAllTeachers() {
       return teacherRepository.getAllTeachers();
    }
    @Override
    public void createTeacher(Teacher teacher){
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(Long teacher_id){
        teacherRepository.deleteById(teacher_id);
    }

    @Override
    public TeacherDTO updateTeacher(Long teacherId, Teacher teacherToUpdate){
        return TeacherConverter.toDTO(teacherRepository.findById(teacherId).map(teacher -> {
            teacher.setName(teacherToUpdate.getName());
            return teacherRepository.save(teacher);
        }).orElseThrow(() -> new RuntimeException("Teacher not found!")));
    }

}
