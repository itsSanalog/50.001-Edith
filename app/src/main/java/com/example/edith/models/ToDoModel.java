package com.example.edith.models;

public class ToDoModel extends TaskID {
    private String taskTitle, taskDescription, taskDueDate;
    private int taskStatus;

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public int getTaskStatus() {
        return taskStatus;
    }
}
