package com.example.edith.models;

import java.time.ZonedDateTime;

public class TaskRequest {
    private String entityName;
    private ZonedDateTime deadline;
    private int duration;
    public TaskRequest(String entityName, ZonedDateTime deadline, int duration) {
        this.entityName = entityName;
        this.deadline = deadline;
        this.duration = duration;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }
    public int getDuration() {
        return duration;
    }

    public String getEntityName() {
        return entityName;
    }
}
