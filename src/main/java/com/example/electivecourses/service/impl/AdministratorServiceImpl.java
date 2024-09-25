package com.example.electivecourses.service.impl;

import com.example.electivecourses.model.entity.Administrator;
import com.example.electivecourses.repository.AdministratorRepository;
import com.example.electivecourses.service.AdministratorService;
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
