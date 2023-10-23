package com.ballastlane.beapp.service;

import com.ballastlane.beapp.errorhandler.CustomErrorException;
import com.ballastlane.beapp.helper.LogHourHelper;
import com.ballastlane.beapp.model.LogHour;
import com.ballastlane.beapp.repository.LogHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class LogHourService {

    @Autowired
    private LogHourRepository logHourRepository;

    @Autowired
    private LogHourHelper logHourHelper;

    public LogHour getLogHourById(Long id) {
        return logHourRepository.findById(id).orElse(null);
    }

    public LogHour updateLogHour(LogHour logHour) {
        int minutes = logHour.getTimeSpent().getMinute();

        if (minutes != LogHourHelper.TIME_SPENT_MINUTES) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    String.format("The time spent should be in %s minutes range", LogHourHelper.TIME_SPENT_MINUTES)
            );
        }

        LocalDate date = logHourHelper.toLocalDate(logHour.getDate());
        LocalDateTime localDateTime = LocalDateTime.of(date, LocalTime.of(0, minutes));
        logHour.setTimeSpent(localDateTime);

        logHourRepository.findById(logHour.getId()).ifPresent(item -> logHourRepository.save(logHour));

        return logHour;
    }

    public void deleteLogHour(Long logHourId) {
        logHourRepository.deleteById(logHourId);
    }
}
