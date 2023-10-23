package com.ballastlane.beapp.service;

import com.ballastlane.beapp.errorhandler.CustomErrorException;
import com.ballastlane.beapp.helper.LogHourHelper;
import com.ballastlane.beapp.helper.StudentHelper;
import com.ballastlane.beapp.model.Course;
import com.ballastlane.beapp.model.LogHour;
import com.ballastlane.beapp.model.Student;
import com.ballastlane.beapp.repository.CourseRepository;
import com.ballastlane.beapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private static final int MAX_COURSES_PER_STUDENT = 3;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LogHourService logHourService;

    @Autowired
    private StudentHelper studentHelper;

    @Autowired
    private LogHourHelper logHourHelper;

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

    public void saveLogHour(Long studentId, Long courseId, LogHour logHour) {
        int minutes = logHour.getTimeSpent().getMinute();

        if (minutes != LogHourHelper.TIME_SPENT_MINUTES) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    String.format("The time spent should be each %s minutes", LogHourHelper.TIME_SPENT_MINUTES)
            );
        }

        LocalDate date = logHourHelper.toLocalDate(logHour.getDate());
        LocalDateTime localDateTime = LocalDateTime.of(date, LocalTime.of(0, minutes));
        Student student = getStudent(studentId);
        logHour.setTimeSpent(localDateTime);

        student.getCourses().stream()
                .filter(course -> course.getId().equals(courseId)).findAny().ifPresent(
                        courseFound -> {
                            courseFound.getLogHours().add(logHour);
                            studentRepository.save(student);
                        }
                );
    }

    public void deleteLogHour(Long studentId, Long courseId, Long logHourId) {
        Student student = getStudent(studentId);

        student.getCourses().stream()
        .filter(course -> course.getId().equals(courseId)).findAny().ifPresent(
                courseFound -> {
                    courseFound.getLogHours().stream()
                            .filter(item -> item.getId().equals(logHourId)).findAny().ifPresent(
                                    item -> {
                                        courseFound.getLogHours().remove(item);
                                        studentRepository.save(student);
                                    }
                            );
                }
        );

        logHourService.deleteLogHour(logHourId);
    }

    private Boolean existEmail(String email) {
        List<Student> students = studentRepository.findByEmail(email);

        return !students.isEmpty();
    }
}
