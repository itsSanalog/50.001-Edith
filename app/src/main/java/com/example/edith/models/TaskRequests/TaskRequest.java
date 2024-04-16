package com.example.edith.models.TaskRequests;

import java.time.ZonedDateTime;

public class TaskRequest {
    private String entityName;
    private String entityDescription;
    private String taskDeadline;
    private int duration;

    public TaskRequest(String entityName, String entityDescription, String taskDeadline, int duration) {
        this.entityName = entityName;
        this.entityDescription = entityDescription;
        this.taskDeadline = taskDeadline;
        this.duration = duration;
    }

    public String getDeadline() {
        return taskDeadline;
    }
    public int getDuration() {
        return duration;
    }

    public String getEntityName() {
        return entityName;
    }
    public String getDescription() {
        return entityDescription;
    }
}
