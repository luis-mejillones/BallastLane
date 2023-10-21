package com.ballastlane.beapp.helper;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.sql.Date;

@Service
public class StudentHelper {
    public static final int MIN_AGE = 16;

    public Boolean isValidAge(Date dob) {
        LocalDate curDate = LocalDate.now();
        int ageStudent = Period.between(dob.toLocalDate(), curDate).getYears();
        return ageStudent < MIN_AGE;
    }
}
