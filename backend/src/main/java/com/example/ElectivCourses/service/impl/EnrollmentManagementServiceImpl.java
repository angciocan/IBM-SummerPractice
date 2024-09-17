package com.example.ElectivCourses.service.impl;

import com.example.ElectivCourses.Model.entity.Course;
import com.example.ElectivCourses.Model.entity.Enrollment;
import com.example.ElectivCourses.Model.entity.EnrollmentStatus;
import com.example.ElectivCourses.repository.CourseRepository;
import com.example.ElectivCourses.repository.EnrollmentManagementRepository;
import com.example.ElectivCourses.repository.EnrollmentRepository;
import com.example.ElectivCourses.service.EnrollmentManagementService;
import com.example.ElectivCourses.service.EnrollmentPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

    @Transactional
    public void processPendingEnrollments(){
        if(enrollmentPeriodService.isEnrollmentPeriodOpen())
        {
            throw new IllegalStateException("Enrollment period is open");
        }
        List<Enrollment> pendingEnrollments = enrollmentManagementRepository.findByStatus(EnrollmentStatus.PENDING);

        Map<Long, List<Enrollment>> enrollmentByCourse = pendingEnrollments.stream()
                .collect(Collectors.groupingBy(enrollment -> enrollment.getCourse().getId()));

        enrollmentByCourse.forEach((courseId, enrollments) -> {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Course not found"));

            enrollments.sort(Comparator.comparingDouble((Enrollment e) -> e.getStudent().getGrade()).reversed());

            int availableEnrollments = course.getMaxStudents();

            for(int i = 0 ; i < enrollments.size() ; i++){
                Enrollment enrollment = enrollments.get(i);
                if( i < availableEnrollments ){
                    enrollment.setStatus(EnrollmentStatus.ENROLLED);
                }
                else{
                    enrollment.setStatus(EnrollmentStatus.CLOSED);
                }
                enrollmentRepository.save(enrollment);
            }

            int enrolledCount = (int) enrollments.stream()
                    .filter(e -> e.getStatus() == EnrollmentStatus.ENROLLED).count();

            course.setMaxStudents(course.getMaxStudents() - enrolledCount);

            courseRepository.save(course);


        });
    }

}
