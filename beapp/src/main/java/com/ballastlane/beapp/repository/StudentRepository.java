package com.ballastlane.beapp.repository;

import com.ballastlane.beapp.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    List<Student> findByEmail(String email);
}
