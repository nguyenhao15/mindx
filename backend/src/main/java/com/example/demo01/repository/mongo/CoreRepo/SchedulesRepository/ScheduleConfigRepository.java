package com.example.demo01.repository.mongo.CoreRepo.SchedulesRepository;

import com.example.demo01.core.Schedulers.model.ScheduleConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleConfigRepository extends MongoRepository<ScheduleConfig, String> {

    ScheduleConfig findByTaskName(String taskName);

    List<ScheduleConfig> findByEnabledTrue();
}
