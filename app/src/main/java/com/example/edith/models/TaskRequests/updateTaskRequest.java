package com.example.edith.models.TaskRequests;

import com.example.edith.models.TaskRequests.TaskRequest;

public class updateTaskRequest extends TaskRequest {
    private String id;
    private String entityName;
    private String entityDescription;
    private String taskDeadline;
    private int duration;

    // Default Constructor
    public updateTaskRequest(String id, String entityName, String entityDescription, String taskDeadline, int duration) {
        super(entityName, entityDescription, taskDeadline, duration);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public String getTaskDeadline() {
        return taskDeadline;
    }

    public int getDuration() {
        return duration;
    }
}
