package com.example.edith.models.TaskRequests;

public class addTaskRequest extends TaskRequest {

    private String taskTitle;
    private String taskDescription;
    private String taskDeadline;
    private int duration;

    // Default constructor
    public addTaskRequest(String taskTitle, String taskDescription, String taskDeadline, int duration) {
        super(taskTitle, taskDescription, taskDeadline, duration);
    }
}
