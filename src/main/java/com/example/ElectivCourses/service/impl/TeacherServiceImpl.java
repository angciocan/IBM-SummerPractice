package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.dto.TeacherProfileDTO;
import com.example.ElectivCourses.Model.entity.Teacher;
import com.example.ElectivCourses.converter.TeacherProfileConverter;
import com.example.ElectivCourses.repository.TeacherRepository;
import com.example.ElectivCourses.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    @Qualifier("customTaskExecutor")
    private Executor executor;

    @Override
    public List<TeacherProfileDTO> getAllTeachers() {
        List<Teacher> teachers  = teacherRepository.findAll();

        Queue<TeacherProfileDTO> dtoQueue = new ConcurrentLinkedQueue<>();

        teachers.forEach(teacher -> executor.execute(() -> {
            TeacherProfileDTO dto = TeacherProfileConverter.toDTO(teacher);
            dtoQueue.add(dto);
        }));

        return new ArrayList<>(dtoQueue);
    }
}
