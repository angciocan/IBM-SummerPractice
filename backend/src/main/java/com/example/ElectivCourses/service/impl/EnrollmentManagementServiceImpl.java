package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.model.entity.Course;
import com.example.ElectivCourses.model.entity.Enrollment;
import com.example.ElectivCourses.model.entity.EnrollmentStatus;
import com.example.ElectivCourses.repository.CourseRepository;
import com.example.ElectivCourses.repository.EnrollmentAdministrationRepository;
import com.example.ElectivCourses.repository.EnrollmentManagementRepository;
import com.example.ElectivCourses.repository.EnrollmentRepository;
import com.example.ElectivCourses.service.EnrollmentAdministrationService;
import com.example.ElectivCourses.service.EnrollmentManagementService;
import com.example.ElectivCourses.service.EnrollmentPeriodService;
import com.example.ElectivCourses.service.EnrollmentService;
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
    private EnrollmentAdministrationService enrollmentAdministrationService;
    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    @Qualifier("customTaskExecutor")
    private Executor executor;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private EnrollmentAdministrationRepository enrollmentAdministrationRepository;

    public void processPendingEnrollments() {
        if (enrollmentPeriodService.isEnrollmentPeriodOpen()) {
            throw new IllegalStateException("Enrollment period is open");
        }

        List<Enrollment> pendingEnrollments = enrollmentManagementRepository.findByStatus(EnrollmentStatus.PENDING);


        Map<Long, List<Enrollment>> enrollmentByCourse = pendingEnrollments.stream()
                .collect(Collectors.groupingBy((Enrollment enrollment) -> enrollment.getCourse().getId()));


        enrollmentByCourse.forEach(this::processCourseEnrollments);
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

            long nrOfCoursesAllowed = enrollmentAdministrationService.nrOfElectiveCoursesByYear(Math.toIntExact(enrollment.getStudent().getStudyYear()));
            int studentNrOfElectiveCourses = enrollmentService.getElectiveCoursesCountByStudentId(enrollment.getStudent().getId());

            System.out.println("Number of courses allowed: " + nrOfCoursesAllowed + "current number of courses:" + studentNrOfElectiveCourses );

            if(i < availableEnrollments && studentNrOfElectiveCourses < nrOfCoursesAllowed) {

                enrollment.setStatus(EnrollmentStatus.ENROLLED);

                course.setMaxStudents(course.getMaxStudents() - 1);

                rabbitTemplate.convertAndSend("enrollment_exchange","enrollment-routing-key","Enrolled student with Id: " + enrollment.getStudent() + "to course with Id:" + enrollment.getCourse());
            } else {
                enrollment.setStatus(EnrollmentStatus.CLOSED);

                rabbitTemplate.convertAndSend("enrollment_exchange","enrollment-routing-key","Rejected student with Id: " + enrollment.getStudent() + "to course with Id:" + enrollment.getCourse());

            }
            enrollmentRepository.save(enrollment);
        }

        int enrolledCount = (int) enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.ENROLLED).count();



        courseRepository.save(course);
    }

}
