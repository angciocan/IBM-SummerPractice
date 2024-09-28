package com.example.electivecourses.service.impl;

import com.example.electivecourses.model.dto.CourseDTO;
import com.example.electivecourses.model.dto.EnrollmentDTO;
import com.example.electivecourses.model.dto.StudentDTO;
import com.example.electivecourses.model.dto.TeacherDTO;
import com.example.electivecourses.model.entity.Course;
import com.example.electivecourses.model.entity.Enrollment;
import com.example.electivecourses.model.entity.EnrollmentStatus;
import com.example.electivecourses.model.entity.Student;
import com.example.electivecourses.converter.EnrollmentConverter;
import com.example.electivecourses.converter.StudentConverter;
import com.example.electivecourses.repository.CourseRepository;
import com.example.electivecourses.repository.EnrollmentRepository;
import com.example.electivecourses.repository.StudentRepository;
import com.example.electivecourses.service.EnrollmentService;
import com.example.electivecourses.service.EnrollmentAdministrationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentAdministrationService enrollmentAdministrationService;

    @Autowired
    private EnrollmentConverter enrollmentConverter;

    @Autowired
    @Qualifier("customTaskExecutor")
    private Executor executor;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public boolean existsByCourseIdAndStudentId(Long courseId, Long studentId) {
        return enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId);
    }

    public void enrollStudentsToMandatoryCourses() {
        System.out.println("Running enrollStudents to automatically enroll students in mandatory courses...");

        List<Student> students = studentRepository.findAll();
        Map<Long, Integer> courseSeatsToDecrement = new ConcurrentHashMap<>();
        List<Enrollment> newEnrollments = Collections.synchronizedList(new ArrayList<>());

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (Student student : students) {

            CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
                    rabbitTemplate.convertAndSend("enrollment-exchange","enrollment-routing-key", student.getId().toString()), executor);
            futures.add(future);
        }

        for (Student student : students) {

            rabbitTemplate.convertAndSend("enrollment-exchange","enrollment-routing-key", student.getId().toString());

            CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
                    processStudentEnrollment(student, newEnrollments, courseSeatsToDecrement), executor);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        courseSeatsToDecrement.forEach(this::adjustCourseSeats);

        enrollmentRepository.saveAll(newEnrollments);
    }


    private void processStudentEnrollment(Student student, List<Enrollment> newEnrollments, Map<Long, Integer> courseSeatsToDecrement) {

        int studyYear = student.getStudyYear();

        int maxMandatoryCourses = enrollmentAdministrationService.nrOfMandatoryCoursesByYear(studyYear);

        List<Course> coursesByStudyYear = courseRepository.findByStudyYear(studyYear);

        List<Course> mandatoryCourses = coursesByStudyYear.stream()
                .filter(course -> course.getCategory().equals("mandatory"))
                .toList();

        if (mandatoryCourses.size() < maxMandatoryCourses) {
            System.out.println("Not enough mandatory courses available for student ID: " + student.getId());
            return;
        }

        for (int i = 0; i < maxMandatoryCourses; i++) {
            Course course = mandatoryCourses.get(i);

            boolean alreadyEnrolled = existsByCourseIdAndStudentId(course.getId(), student.getId());

            if (!alreadyEnrolled) {
                Enrollment newEnrollment = new Enrollment();
                newEnrollment.setCourse(course);
                newEnrollment.setStudent(student);
                newEnrollment.setStatus(EnrollmentStatus.ENROLLED);

                newEnrollments.add(newEnrollment);

                courseSeatsToDecrement.merge(course.getId(), 1, Integer::sum);
            }
        }
        rabbitTemplate.convertAndSend("enrollment-exchange", "enrollment-routing-key", "Finished processing for student ID: " + student.getId());


    }

    private void adjustCourseSeats(Long courseId, int seatsToDecrement) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course not found with ID: " + courseId));

        if (course.getMaxStudents() < seatsToDecrement) {
            throw new IllegalStateException("Course with ID: " + course.getId() + " does not have enough seats available.");
        }

        course.setMaxStudents(course.getMaxStudents() - seatsToDecrement);
        courseRepository.save(course);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fixEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        Map<Long, Integer> courseSeatsToDecrement = new ConcurrentHashMap<>();

        CountDownLatch latch = new CountDownLatch(enrollments.size());

        for (Enrollment enrollment : enrollments) {
            executor.execute(() -> {
                try {
                    processEnrollment(enrollment, courseSeatsToDecrement);
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Fixing enrollments was interrupted", e);
        }

        courseSeatsToDecrement.forEach(this::adjustCourseSeats);

        enrollStudentsToMandatoryCourses();

        enrollmentRepository.saveAll(enrollments);
    }

    private void processEnrollment(Enrollment enrollment, Map<Long, Integer> courseSeatsToDecrement) {
        Course course = enrollment.getCourse();
        Student student = enrollment.getStudent();

        if (course.getCategory().equals("mandatory") && !enrollment.getStatus().equals(EnrollmentStatus.ENROLLED)) {


            enrollment.setStatus(EnrollmentStatus.ENROLLED);
            courseSeatsToDecrement.merge(course.getId(), 1, Integer::sum);
        }

        if (student.getStudyYear() != course.getStudyYear()) {
            throw new IllegalStateException("Study year mismatch for student ID: " + student.getId() + " and course ID: " + course.getId());
        }

        int maxMandatoryCourses = enrollmentAdministrationService.nrOfMandatoryCoursesByYear(student.getStudyYear());
        long currentMandatoryCourses = enrollmentRepository.findAll().stream()
                .filter(e -> e.getStudent().getId().equals(student.getId()) && e.getCourse().getCategory().equals("mandatory"))
                .count();

        if (currentMandatoryCourses > maxMandatoryCourses) {
            throw new IllegalStateException("Student has exceeded the max number of mandatory courses for their study year: " + student.getId());
        }
    }

    @Override
    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        Enrollment newEnrollment = enrollmentConverter.toEntity(enrollmentDTO);
        newEnrollment.setStatus(EnrollmentStatus.PENDING);

        int studentStudyYear = newEnrollment.getStudent().getStudyYear();
        int courseStudyYear = newEnrollment.getCourse().getStudyYear();

        if (studentStudyYear != courseStudyYear) {
            throw new IllegalArgumentException("The study year of the student does not match the study year of the course.");
        }

        int maxMandatoryCourses = enrollmentAdministrationService.nrOfMandatoryCoursesByYear(studentStudyYear);

        Enrollment finalNewEnrollment1 = newEnrollment;
        long currentMandatoryCourses = enrollmentRepository.findAll().stream()
                .filter(enrollment ->
                        enrollment.getStudent().getId().equals(finalNewEnrollment1.getStudent().getId()) &&
                                enrollment.getCourse().getCategory().equals("mandatory")
                ).count();

        if (newEnrollment.getCourse().getCategory().equals("mandatory") && currentMandatoryCourses >= maxMandatoryCourses) {
            throw new IllegalArgumentException("The student has already reached the maximum number of mandatory courses for their study year.");
        }
        if(newEnrollment.getCourse().getCategory().equals("mandatory")){
            newEnrollment.setStatus(EnrollmentStatus.ENROLLED);
        }

        Enrollment finalNewEnrollment = newEnrollment;
        Optional<Enrollment> alreadyExists = enrollmentRepository.findAll().stream()
                .filter(enrollment ->
                        enrollment.getStudent().getId().equals(finalNewEnrollment.getStudent().getId()) &&
                                enrollment.getCourse().getId().equals(finalNewEnrollment.getCourse().getId())
                ).findAny();

        if (alreadyExists.isPresent()) {
            return EnrollmentConverter.toDTO(alreadyExists.get());
        }

        Course course = courseRepository.findById(newEnrollment.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.getMaxStudents() <= 0) {
            throw new IllegalArgumentException("No more spaces available in the course.");
        }

        course.setMaxStudents(course.getMaxStudents() - 1);

        courseRepository.save(course);

        newEnrollment = enrollmentRepository.save(newEnrollment);

        rabbitTemplate.convertAndSend("enrollment-exchange","enrollment-routing-key", "Finished creating enrollment for student ID: " + newEnrollment.getStudent().getId() + " for course ID: " + newEnrollment.getCourse().getId());


        return EnrollmentConverter.toDTO(newEnrollment);
    }

    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        rabbitTemplate.convertAndSend("enrollment-exchange","enrollment-routing-key", "Fetched all the enrollments");

        return enrollmentRepository.findAll().stream().map(EnrollmentConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteEnrollment(Long studentId, Long courseId) {

        rabbitTemplate.convertAndSend("enrollment-exchange","enrollment-routing-key", "Deleting enrollment for student ID: " + studentId + "To course ID: " + courseId);

        Optional<Enrollment> existentEnrollment = enrollmentRepository.findAll().stream()
                .filter(enrollment -> Objects.equals(enrollment.getStudent().getId(), studentId))
                .filter(enrollment -> Objects.equals(enrollment.getCourse().getId(), courseId))
                .findAny();

        existentEnrollment.ifPresent(enrollment -> enrollmentRepository.delete(enrollment));

    }

    @Override
    public long getNrOfCurrentApplications(Long courseId) {

        rabbitTemplate.convertAndSend("enrollment-exchange","enrollment-routing-key", "Getting number of applications to course: " + courseId );

        return enrollmentRepository.findAll().stream()
                .filter(enrollment -> Objects.equals(enrollment.getCourse().getId(), courseId)).filter(enrollment -> enrollment.getStatus() == EnrollmentStatus.PENDING).count();
    }

    @Override
    public List<StudentDTO> getStudentsPendingEnrollmentToCourse(Long courseId) {

        rabbitTemplate.convertAndSend("enrollment-exchange","enrollment-routing-key", "Getting pending enrollments to course: " + courseId );


        return enrollmentRepository.findAll().stream()
                .filter(enrollment -> Objects.equals(enrollment.getCourse().getId(), courseId)).filter(enrollment -> enrollment.getStatus() == EnrollmentStatus.PENDING)
                .map(enrollment -> StudentConverter.toDTO(enrollment.getStudent())).collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getStudentsEnrolledToCourse(Long courseId) {

        rabbitTemplate.convertAndSend("enrollment-exchange","enrollment-routing-key", "Getting students enrolled to course:" + courseId );


        return enrollmentRepository.findAll().stream()
                .filter(enrollment -> Objects.equals(enrollment.getCourse().getId(), courseId)).filter(enrollment -> enrollment.getStatus() == EnrollmentStatus.ENROLLED)
                .map(enrollment -> StudentConverter.toDTO(enrollment.getStudent())).collect(Collectors.toList());
    }
    @Override
    public List<CourseDTO> getCoursesForStudent(Long studentId) {

        rabbitTemplate.convertAndSend("enrollment-exchange","enrollment-routing-key", "Getting courses for student: " + studentId );


        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .map(enrollment -> {
                    TeacherDTO teacherDTO = new TeacherDTO(
                            enrollment.getCourse().getTeacher().getId(),
                            enrollment.getCourse().getTeacher().getName()
                    );
                    return new CourseDTO(
                            enrollment.getCourse().getCourseName(),
                            enrollment.getCourse().getMaxStudents(),
                            enrollment.getCourse().getStudyYear(),
                            enrollment.getCourse().getCategory(),
                            enrollment.getCourse().getDayOfWeek(),
                            enrollment.getCourse().getTime(),
                            teacherDTO,
                            enrollment.getCourse().getId()
                    );
                })
                .collect(Collectors.toList());
    }
}
