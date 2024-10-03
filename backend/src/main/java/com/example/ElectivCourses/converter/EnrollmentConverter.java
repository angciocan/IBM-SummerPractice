package com.example.ElectivCourses.converter;
import com.example.ElectivCourses.model.dto.EnrollmentDTO;
import com.example.ElectivCourses.model.entity.Enrollment;
import com.example.ElectivCourses.model.entity.EnrollmentStatus;
import com.example.ElectivCourses.repository.CourseRepository;
import com.example.ElectivCourses.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class EnrollmentConverter {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseRepository courseRepository;

    public static EnrollmentDTO toDTO(Enrollment enrollment) {
        if (enrollment == null) {
            return null;
        }

        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setId(enrollment.getId());
        enrollmentDTO.setStudentId(enrollment.getStudent() != null ? enrollment.getStudent().getId() : null);
        enrollmentDTO.setCourseId(enrollment.getCourse() != null ? enrollment.getCourse().getId() : null);
        enrollmentDTO.setStatus(enrollment.getStatus() != null ? enrollment.getStatus() : null);

        return enrollmentDTO;
    }

    public Enrollment toEntity(EnrollmentDTO enrollmentDTO) {
        if (enrollmentDTO == null) {
            return null;
        }

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(studentService.getStudentById(enrollmentDTO.getStudentId()));

        enrollment.setCourse(courseRepository.getReferenceById(enrollmentDTO.getCourseId()));

        enrollment.setStatus(EnrollmentStatus.PENDING);

        return enrollment;

    }
}

