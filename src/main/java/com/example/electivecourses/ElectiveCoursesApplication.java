package com.example.electivecourses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ElectiveCoursesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectiveCoursesApplication.class, args);
	}

}
