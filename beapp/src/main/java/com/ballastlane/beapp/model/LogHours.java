package com.ballastlane.beapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_hours")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    private TaskCategory taskCategory;

    @Column(nullable = false)
    private String taskDescription;

    @Column(nullable = false)
    private LocalDateTime timeSpent;


}
