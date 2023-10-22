package com.ballastlane.beapp.service;

import com.ballastlane.beapp.errorhandler.CustomErrorException;
import com.ballastlane.beapp.helper.StudentHelper;
import com.ballastlane.beapp.model.Course;
import com.ballastlane.beapp.model.LogHours;
import com.ballastlane.beapp.model.Student;
import com.ballastlane.beapp.repository.CourseRepository;
import com.ballastlane.beapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private static final int MAX_COURSES_PER_STUDENT = 3;
    private static final int TIME_SPENT_MINUTES = 30;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentHelper studentHelper;

    @Autowired
    private CourseRepository courseRepository;

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student saveStudent(Student student) throws Exception {
        if (studentHelper.isValidAge(student.getDateOfBirth())) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Minimum 16 years old"
            );
        }

        if (existEmail(student.getEmail())) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Email already registered"
            );
        }

        return studentRepository.save(student);
    }

    public void takeCourses(Long studentId, List<Long> courseIds) {
        List<Long> courseIdsWithoutDuplicates = courseIds.stream()
                .distinct()
                .collect(Collectors.toList());

        if (courseIdsWithoutDuplicates.size() > MAX_COURSES_PER_STUDENT) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Students can't take more than %s courses", MAX_COURSES_PER_STUDENT)
            );
        }

        Student student = getStudent(studentId);
        int totalCourses = student.getCourses().size() + courseIdsWithoutDuplicates.size();

        if (totalCourses > MAX_COURSES_PER_STUDENT) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Students can't take more than %s courses", MAX_COURSES_PER_STUDENT)
            );
        }

        Set<Course> courses = new HashSet<>();
        courseRepository.findAllById(courseIdsWithoutDuplicates).forEach(courses::add);

        student.getCourses().addAll(courses);
        studentRepository.save(student);
    }

    public void saveLogHours(Long studentId, LogHours logHours) {
        int minutes = logHours.getTimeSpent().getMinute();

        if (minutes != TIME_SPENT_MINUTES) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    String.format("The time spent should be in %s minutes range", TIME_SPENT_MINUTES)
            );
        }

        Student student = getStudent(studentId);
        student.getLogHours().add(logHours);
        studentRepository.save(student);
    }

    private Boolean existEmail(String email) {
        List<Student> students = studentRepository.findByEmail(email);

        return !students.isEmpty();
    }
}
