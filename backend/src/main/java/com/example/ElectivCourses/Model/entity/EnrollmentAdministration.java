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
    @Column
    private int studyYear;

    @Column
    private int nrOfCourses;

    @Transient
    private LocalDate startTime;

    @Transient
    private LocalDate endTime;
//
//    public EnrollmentAdministration(int studyYear, int nrOfCourses, LocalDate startTime, LocalDate endTime) {
//        this.studyYear = studyYear;
//        this.nrOfCourses = nrOfCourses;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }
//
//    public EnrollmentAdministration() {
//    }
//
//    public int getStudyYear() {
//        return studyYear;
//    }
//
//    public void setStudyYear(int studyYear) {
//        this.studyYear = studyYear;
//    }
//
//    public int getNrOfCourses() {
//        return nrOfCourses;
//    }
//
//    public void setNrOfCourses(int nrOfCourses) {
//        this.nrOfCourses = nrOfCourses;
//    }
//
//    public LocalDate getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(LocalDate endTime) {
//        this.endTime = endTime;
//    }
//
//    public LocalDate getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(LocalDate startTime) {
//        this.startTime = startTime;
//    }
}
