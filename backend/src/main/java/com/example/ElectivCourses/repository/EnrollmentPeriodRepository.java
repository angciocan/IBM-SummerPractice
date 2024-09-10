package com.example.ElectivCourses.repository;

import com.example.ElectivCourses.Model.entity.EnrollmentPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentPeriodRepository extends JpaRepository<EnrollmentPeriod, Long> {
    default EnrollmentPeriod findOrCreateSingleton(){
        return findById(1L).orElseGet(() ->{
            EnrollmentPeriod newPeriod = new EnrollmentPeriod();
            newPeriod.setId(1L);
            newPeriod.setOpen(false);
            return save(newPeriod);
        });
    }
}
