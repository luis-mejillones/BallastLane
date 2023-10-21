package com.ballastlane.beapp.helper;

import com.ballastlane.beapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class CourseHelper {
    @Autowired
    private UserService userService;

    public Boolean isUserAdmin(String userEmail) {
        return userService.isUserAdmin(userEmail);
    }

    public Date addMonths(Date initialDate, Integer months) {
        LocalDate newDate = initialDate.toLocalDate().plusMonths(months);

        return Date.valueOf(newDate);
    }
}
