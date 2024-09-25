package com.example.electivecourses.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollment_administration")
public class EnrollmentAdministration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private int studyYear;

    @Column
    private int nrOfMandatoryCourses;
    @Column
    private int nrOfElectiveCourses;

    @Transient
    private static LocalDate startTime;

    @Transient
    private static LocalDate endTime;

    public EnrollmentAdministration() {}

    public EnrollmentAdministration(int studyYear, int nrOfMandatoryCourses, int nrOfElectiveCourses) {
        this.studyYear = studyYear;
        this.nrOfMandatoryCourses = nrOfMandatoryCourses;
        this.nrOfElectiveCourses = nrOfElectiveCourses;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public void setNrOfMandatoryCourses(int nrOfMandatoryCourses) {
        this.nrOfMandatoryCourses = nrOfMandatoryCourses;
    }

    public void setNrOfElectiveCourses(int nrOfElectiveCourses) {
        this.nrOfElectiveCourses = nrOfElectiveCourses;
    }

    public int getNrOfMandatoryCourses() {
        return nrOfMandatoryCourses;
    }

    public int getNrOfElectiveCourses() {
        return nrOfElectiveCourses;
    }

    public static LocalDate getStartTime() {
        return startTime;
    }

    public static void setStartTime(LocalDate startTime) {
        EnrollmentAdministration.startTime = startTime;
    }

    public static LocalDate getEndTime() {
        return endTime;
    }

    public static void setEndTime(LocalDate endTime) {
        EnrollmentAdministration.endTime = endTime;
    }
}
