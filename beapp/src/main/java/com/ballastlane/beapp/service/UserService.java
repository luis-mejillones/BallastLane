package com.ballastlane.beapp.service;

import com.ballastlane.beapp.model.Role;
import com.ballastlane.beapp.model.User;
import com.ballastlane.beapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Boolean isUserAdmin(String userEmail) {
        List<User> users = userRepository.findByEmailAndRole(userEmail, Role.ADMINISTRATOR);

        return !users.isEmpty();
    }
}
