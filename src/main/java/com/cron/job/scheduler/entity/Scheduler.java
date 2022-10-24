package com.cron.job.scheduler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scheduler")
public class Scheduler {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "cron_expression")
    private String cronExpression;

    public Scheduler() {
    }

    public Scheduler(String id, String cronExpression) {
        this.id = id;
        this.cronExpression = cronExpression;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}