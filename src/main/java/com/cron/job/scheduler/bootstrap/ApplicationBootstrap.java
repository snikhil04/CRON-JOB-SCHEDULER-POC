package com.cron.job.scheduler.bootstrap;

import com.cron.job.scheduler.repository.SchedulerRepository;
import com.cron.job.scheduler.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ApplicationBootstrap {

    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private SchedulerRepository schedulerRepository;

    @PostConstruct
    public void checkAndRestartSchedulers() {
        schedulerRepository.findAll().forEach(taskDefinition -> {
            schedulerService.restartReportScheduler(taskDefinition);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Error While Restarting Scheduler");
            }
        });
    }
}