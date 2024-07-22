package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.entity.Administrator;
import com.example.ElectivCourses.repository.AdministratorRepository;
import com.example.ElectivCourses.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public List<Administrator> getAllAdministrators() {
        return administratorRepository.findAll();
    }



}
