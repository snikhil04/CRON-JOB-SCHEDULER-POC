package com.cron.job.scheduler.controller;

import com.cron.job.scheduler.service.SchedulerService;
import com.cron.job.scheduler.entity.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/scheduler")
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping
    public String scheduleATask(@RequestParam(name = "cronExpression") String cronExpression) {
        return schedulerService.createScheduler(cronExpression);
    }

    @GetMapping
    public List<Scheduler> getAllSchedulers(@RequestParam(name = "id",required = false) String id) {
       return schedulerService.getAllSchedulers(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public String removeJob(@PathVariable String id) {
       return schedulerService.removeScheduledTask(id);
    }
}