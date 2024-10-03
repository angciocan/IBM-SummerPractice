package com.example.electivecourses.service.impl;

import com.example.electivecourses.model.entity.Course;
import com.example.electivecourses.model.entity.Enrollment;
import com.example.electivecourses.model.entity.EnrollmentStatus;
import com.example.electivecourses.repository.CourseRepository;
import com.example.electivecourses.repository.EnrollmentManagementRepository;
import com.example.electivecourses.repository.EnrollmentRepository;
import com.example.electivecourses.service.EnrollmentManagementService;
import com.example.electivecourses.service.EnrollmentPeriodService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
public class EnrollmentManagementServiceImpl implements EnrollmentManagementService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private EnrollmentManagementRepository enrollmentManagementRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EnrollmentPeriodService enrollmentPeriodService;

    @Autowired
    @Qualifier("customTaskExecutor")
    private Executor executor;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void processPendingEnrollments() {
        if (enrollmentPeriodService.isEnrollmentPeriodOpen()) {
            throw new IllegalStateException("Enrollment period is open");
        }

        List<Enrollment> pendingEnrollments = enrollmentManagementRepository.findByStatus(EnrollmentStatus.PENDING);


        Map<Long, List<Enrollment>> enrollmentByCourse = pendingEnrollments.stream()
                .collect(Collectors.groupingBy((Enrollment enrollment) -> enrollment.getCourse().getId()));


        enrollmentByCourse.forEach((courseId, enrollments) ->
                executor.execute(() -> processCourseEnrollments(courseId, enrollments))
        );
    }

    private void processCourseEnrollments(Long courseId, List<Enrollment> enrollments) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        enrollments = enrollments.stream()
                .sorted((e1, e2) -> Double.compare(e2.getStudent().getGrade(), e1.getStudent().getGrade()))
                .collect(Collectors.toList());


        int availableEnrollments = course.getMaxStudents();

        for (int i = 0; i < enrollments.size(); i++) {

            Enrollment enrollment = enrollments.get(i);
            if (i < availableEnrollments) {

                enrollment.setStatus(EnrollmentStatus.ENROLLED);

                rabbitTemplate.convertAndSend("enrollment_exchange","enrollment-routing-key","Enrolled student with Id: " + enrollment.getStudent() + "to course with Id:" + enrollment.getCourse());


            } else {
                enrollment.setStatus(EnrollmentStatus.CLOSED);

                rabbitTemplate.convertAndSend("enrollment_exchange","enrollment-routing-key","Rejected student with Id: " + enrollment.getStudent() + "to course with Id:" + enrollment.getCourse());

            }
            enrollmentRepository.save(enrollment);
        }

        int enrolledCount = (int) enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.ENROLLED).count();

        course.setMaxStudents(course.getMaxStudents() - enrolledCount);

        courseRepository.save(course);
    }



}
