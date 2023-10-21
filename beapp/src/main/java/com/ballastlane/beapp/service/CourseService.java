package com.ballastlane.beapp.service;

import com.ballastlane.beapp.errorhandler.CustomErrorException;
import com.ballastlane.beapp.helper.CourseHelper;
import com.ballastlane.beapp.model.Course;
import com.ballastlane.beapp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class CourseService {
    private static final int COURSE_DURATION_MONTHS = 6;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseHelper courseHelper;

    public Course saveCourse(String userEmail, Course course) {
        if (!courseHelper.isUserAdmin(userEmail)) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Course creation must be done by an Administrator user"
            );
        }
        if (existCourseName(course.getName())) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Course Name already exists"
            );
        }

        Date endDate = courseHelper.addMonths(course.getDateOfCreation(), COURSE_DURATION_MONTHS);
        course.setDateOfEnd(endDate);

        return courseRepository.save(course);
    }

    private Boolean existCourseName(String name) {
        List<Course> courses = courseRepository.findByName(name);
        return !courses.isEmpty();
    }
}
