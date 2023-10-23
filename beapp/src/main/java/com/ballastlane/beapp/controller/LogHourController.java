package com.ballastlane.beapp.controller;

import com.ballastlane.beapp.model.LogHour;
import com.ballastlane.beapp.service.LogHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogHourController {
    @Autowired
    private LogHourService logHourService;

    @PutMapping("/log-hour")
    @ResponseStatus(code = HttpStatus.OK)
    public LogHour updateCourse(
            @RequestBody LogHour logHour
    ) throws Exception {
        return logHourService.updateLogHour(logHour);
    }
}
