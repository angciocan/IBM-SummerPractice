package com.example.ElectivCourses.Model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "enrollment_period")
public class EnrollmentPeriod {

    @Id
    private Long id;

    private boolean isOpen;

    public void setId(Long id) {
        this.id = id;
    }

    public EnrollmentPeriod(){

    }

    public Long getId() {
        return id;
    }

    public void setOpen(boolean open){
        isOpen = open;
    }

    public boolean isOpen(){
        return isOpen;
    }
}
