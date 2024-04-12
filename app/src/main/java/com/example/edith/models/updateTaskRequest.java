package com.example.edith.models;

public class updateTaskRequest extends TaskRequest{
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
}
