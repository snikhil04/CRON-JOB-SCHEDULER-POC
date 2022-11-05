package com.cron.job.scheduler.service;

import com.cron.job.scheduler.entity.Scheduler;
import com.cron.job.scheduler.exception.ValidationException;
import com.cron.job.scheduler.repository.SchedulerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
public class SchedulerService {

    @Bean
    private TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }
    @Autowired
    private SchedulerRepository schedulerRepository;
    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public String createScheduler(String cronExpression) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Scheduler scheduler = saveSchedulerInDatabase(cronExpression, id);
        log.info("Scheduling task with job id: " + scheduler.getId() + " and cron expression: " + scheduler.getCronExpression());
        ScheduledFuture<?> scheduledTask = taskScheduler().schedule(()->{try {System.out.println("Scheduler Running : " + new Date());} catch (Exception e) {log.error("Error In Custom Scheduler : " + e.getMessage());}}, new CronTrigger(cronExpression, TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        jobsMap.put(id, scheduledTask);
        return "scheduler created successfully.";
    }

    public void restartReportScheduler(Scheduler scheduler) {
        log.info("Scheduling task with job id: " + scheduler.getId() + " and cron expression: " + scheduler.getCronExpression());
        ScheduledFuture<?> scheduledTask = taskScheduler().schedule(()->{try {System.out.println("Scheduler Running : " + new Date());} catch (Exception e) {log.error("Error In Custom Scheduler : " + e.getMessage());}}, new CronTrigger(scheduler.getCronExpression(), TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        jobsMap.put(scheduler.getId(), scheduledTask);
    }

    public List<Scheduler> getAllSchedulers(String id) {
        if (Objects.nonNull(id) && !id.isEmpty()) {
            Scheduler scheduler = schedulerRepository.findById(id).orElseThrow(() -> new ValidationException(HttpStatus.BAD_REQUEST.value(), "Scheduler not found."));
            return Collections.singletonList(scheduler);
        }
        return schedulerRepository.findAll();
    }

    public String removeScheduledTask(String id) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(id);
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(id, null);
            schedulerRepository.deleteById(id);
            return "scheduler Stopped Successfully";
        }
        return "";
    }

    public Scheduler saveSchedulerInDatabase(String cronExpression, String id) {
        Scheduler scheduler = new Scheduler();
        scheduler.setId(id);
        scheduler.setCronExpression(cronExpression);
        schedulerRepository.save(scheduler);
        return scheduler;
    }
}