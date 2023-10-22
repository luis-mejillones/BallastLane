package com.ballastlane.beapp.service;

import com.ballastlane.beapp.errorhandler.CustomErrorException;
import com.ballastlane.beapp.helper.CourseHelper;
import com.ballastlane.beapp.model.Course;
import com.ballastlane.beapp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        Date endDate = courseHelper.addMonths(course.getStartDate(), COURSE_DURATION_MONTHS);
        course.setEndDate(endDate);

        return courseRepository.save(course);
    }

    public Course updateCourse(String userEmail, Course course) {
        if (!courseHelper.isUserAdmin(userEmail)) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Course creation must be done by an Administrator user"
            );
        }
        Optional<Course> optionalCourse = courseRepository.findById(course.getId());

        if (optionalCourse.isPresent()) {
            Date endDate = courseHelper.addMonths(course.getStartDate(), COURSE_DURATION_MONTHS);
            course.setEndDate(endDate);

            return courseRepository.save(course);
        }

        course.setId(null);
        if (existCourseName(course.getName())) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Course Name already exists"
            );
        }

        Date endDate = courseHelper.addMonths(course.getStartDate(), COURSE_DURATION_MONTHS);
        course.setEndDate(endDate);

        return courseRepository.save(course);
    }

    public List<Course> getAvailableCourses() {
        List<Course> courses = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis());
        courseRepository.findAll().forEach(
                item -> {
                    if (item.getEndDate().before(currentDate)) {
                        courses.add(item);
                    }
                }
            );

        return courses;
    }

    private Boolean existCourseName(String name) {
        List<Course> courses = courseRepository.findByName(name);
        return !courses.isEmpty();
    }

    private Course updateCourse(Course currentCourse, Course newCourse) {
        if (currentCourse.getName().equals(newCourse.getName())) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Course Name already exists"
            );
        }

        return courseRepository.save(newCourse);
    }
}
