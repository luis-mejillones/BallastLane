package com.ballastlane.beapp.controller;

import com.ballastlane.beapp.model.LogHours;
import com.ballastlane.beapp.model.Student;
import com.ballastlane.beapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PostMapping("/student")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Student saveStudent(@RequestBody Student student) throws Exception {
        return studentService.saveStudent(student);
    }

    @PostMapping("/student/{studentId}/take-courses")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void takeCourses(
            @PathVariable Long studentId,
            @RequestBody List<Long> courseIds
    ) throws Exception {
        studentService.takeCourses(studentId, courseIds);
    }

    @PostMapping("/student/{studentId}/log-hours")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void saveLogHours(
            @PathVariable Long studentId,
            @RequestBody LogHours logHours
    ) throws Exception {
        studentService.saveLogHours(studentId, logHours);
    }
}
