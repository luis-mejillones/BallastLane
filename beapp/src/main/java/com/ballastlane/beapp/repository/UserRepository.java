package com.ballastlane.beapp.repository;

import com.ballastlane.beapp.model.Role;
import com.ballastlane.beapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByEmailAndRole(String email, Role role);
}
