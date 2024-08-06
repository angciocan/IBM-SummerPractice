package com.example.ElectivCourses.Model.entity;
import java.util.HashMap;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashMap;

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
    private int nrOfCourses;

    @Transient
    private static LocalDate startTime;

    @Transient
    private static LocalDate endTime;

    public EnrollmentAdministration(Long id, int nrOfCourses, int studyYear) {
        this.id = id;
        this.nrOfCourses = nrOfCourses;
        this.studyYear = studyYear;
    }

    public EnrollmentAdministration(int studyYear, int nrOfCourses) {
        this.studyYear = studyYear;
        this.nrOfCourses = nrOfCourses;
    }

    public EnrollmentAdministration() {}

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

    public int getNrOfCourses() {
        return nrOfCourses;
    }

    public void setNrOfCourses(int nrOfCourses) {
        this.nrOfCourses = nrOfCourses;
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
