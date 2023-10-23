package com.ballastlane.beapp.repository;

import com.ballastlane.beapp.model.LogHour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogHourRepository extends CrudRepository<LogHour, Long>  {
}
