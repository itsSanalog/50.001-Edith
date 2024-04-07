package com.example.edith.models;

public class ToDoModel extends TaskID {
    private String taskTitle, taskDescription, taskDueDate, orderDate;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
    }
}
