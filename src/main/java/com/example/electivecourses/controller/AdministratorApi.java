package com.example.electivecourses.controller;

import com.example.electivecourses.model.entity.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.electivecourses.service.AdministratorService;

import java.util.List;

@RestController
@RequestMapping("/administrator")
@CrossOrigin(origins = "http://localhost:4200")
public class AdministratorApi {
    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/")
    @ResponseBody
    public List<Administrator> getAllAdministrators() {
        return administratorService.getAllAdministrators();
    }


}
