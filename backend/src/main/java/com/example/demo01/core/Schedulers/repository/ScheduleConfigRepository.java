package com.example.demo01.core.Schedulers.repository;

import com.example.demo01.core.Schedulers.model.ScheduleConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScheduleConfigRepository extends MongoRepository<ScheduleConfig, String> {

    ScheduleConfig findByTaskName(String taskName);

    List<ScheduleConfig> findByEnabledTrue();
}
