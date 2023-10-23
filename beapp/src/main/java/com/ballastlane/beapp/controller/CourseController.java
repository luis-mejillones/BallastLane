package com.ballastlane.beapp.controller;

import com.ballastlane.beapp.model.Course;
import com.ballastlane.beapp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/course/{userEmail}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course saveCourse(@PathVariable String userEmail, @RequestBody Course course) throws Exception {
        return courseService.saveCourse(userEmail, course);
    }

    @PutMapping("/course/{userEmail}")
    @ResponseStatus(code = HttpStatus.OK)
    public Course updateCourse(@PathVariable String userEmail, @RequestBody Course course) throws Exception {
        return courseService.updateCourse(userEmail, course);
    }

    @GetMapping("/course")
    public List<Course> getAvailableCourses() {
        return courseService.getAvailableCourses();
    }
}
