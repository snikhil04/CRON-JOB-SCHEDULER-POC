package com.cron.job.scheduler.service;

import com.cron.job.scheduler.entity.Scheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class SchedulerThread implements Runnable {

    private Scheduler scheduler;

    @Override
    public void run() {
        try {
            System.out.println("Scheduler Running : " + new Date());
        } catch (Exception e) {
            log.error("Error In Custom Scheduler : " + e.getMessage());
        }
    }

    public void setTaskDefinition(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}