package com.example.edith.models.TaskRequests;

/**
 * addTaskRequest is a class that extends TaskRequest and represents a task request with a task title, task description, task deadline, and duration.
 * It provides a constructor to initialize these values.
 */
public class addTaskRequest extends TaskRequest {

    private String taskTitle;
    private String taskDescription;
    private String taskDeadline;
    private int duration;

    /**
     * Constructor for addTaskRequest.
     * Initializes taskTitle, taskDescription, taskDeadline, and duration with the provided values.
     * @param taskTitle The title of the task.
     * @param taskDescription The description of the task.
     * @param taskDeadline The deadline of the task.
     * @param duration The duration of the task.
     */
    public addTaskRequest(String taskTitle, String taskDescription, String taskDeadline, int duration) {
        super(taskTitle, taskDescription, taskDeadline, duration);
    }
}