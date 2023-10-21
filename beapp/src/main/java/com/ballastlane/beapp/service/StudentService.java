package com.ballastlane.beapp.service;

import com.ballastlane.beapp.model.Student;
import com.ballastlane.beapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student saveStudent(Student student) throws Exception {
        LocalDate curDate = LocalDate.now();
        int ageStudent = Period.between(student.getDob().toLocalDate(), curDate).getYears();
        if (ageStudent < 16) {
            throw new Exception("Minimum 16 years old");
        }
        return studentRepository.save(student);
    }
}
