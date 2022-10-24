package com.cron.job.scheduler.repository;

import com.cron.job.scheduler.entity.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulerRepository extends JpaRepository<Scheduler, String> {
}
