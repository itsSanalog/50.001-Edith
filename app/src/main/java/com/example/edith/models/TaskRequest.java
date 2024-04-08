package com.example.edith.models;



public class TaskRequest {
    private String entityName;
    private String deadline;
    private long duration;
    public TaskRequest(String entityName, String deadline, long duration) {
        this.entityName = entityName;
        this.deadline = deadline;
        this.duration = duration;
    }

    public String getDeadline() {
        return deadline;
    }
    public long getDuration() {
        return duration;
    }

    public String getEntityName() {
        return entityName;
    }
}
