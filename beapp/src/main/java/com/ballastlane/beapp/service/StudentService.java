package com.ballastlane.beapp.service;

import com.ballastlane.beapp.errorhandler.CustomErrorException;
import com.ballastlane.beapp.helper.StudentHelper;
import com.ballastlane.beapp.model.Student;
import com.ballastlane.beapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentHelper studentHelper;

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

    private Boolean existEmail(String email) {
        List<Student> students = studentRepository.findByEmail(email);
        return !students.isEmpty();
    }
}
