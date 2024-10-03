package com.example.electivecourses.model.defaults;

import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class EnrollmentAdministrationDefaults {
    private static final Map<Integer, DefaultCourses> defaultCoursesByYear = new HashMap<>();

    static {
        defaultCoursesByYear.put(2, new DefaultCourses(4, 2)); // Default values for year 2
        defaultCoursesByYear.put(3, new DefaultCourses(3, 3)); // Default values for year 3
        defaultCoursesByYear.put(4, new DefaultCourses(2, 4)); // Default values for year 4
    }

    public DefaultCourses getDefaultValuesByStudyYear(int studyYear) {
        return defaultCoursesByYear.get(studyYear);
    }

    public record DefaultCourses(int nrOfMandatoryCourses, int nrOfElectiveCourses) {}

}
