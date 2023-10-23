package com.ballastlane.beapp.helper;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;

@Service
public class LogHourHelper {
    public static final int TIME_SPENT_MINUTES = 30;

    public LocalDate toLocalDate(java.sql.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        return LocalDate.of(year, month, day);
    }
}
