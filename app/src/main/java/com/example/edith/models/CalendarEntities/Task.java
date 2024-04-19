package com.example.edith.models.CalendarEntities;

import com.example.edith.controllers.SchedulerController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Task is a class that extends CalendarEntity and represents a task.
 * It provides a default constructor and a constructor to initialize the task title, start time, end time, description, and deadline.
 * It also provides methods to get and set these values, check if the task is reschedulable, completed, or overdue, and convert the start date to different formats.
 */
public class Task extends CalendarEntity {
    private boolean isCompleted;
    private boolean isOverdue;
    private String deadline;

    /**
     * Default constructor for Task.
     * Calls the constructor of the superclass CalendarEntity with null values.
     */
    public Task() {
        super(null,null,null,null);
    }

    /**
     * Constructor for Task.
     * Initializes the task title, start time, end time, description, and deadline with the provided values.
     * Sets the type of the CalendarEntity to "Task".
     * @param taskTitle The title of the task.
     * @param startTime The start time of the task.
     * @param endTime The end time of the task.
     * @param description The description of the task.
     * @param deadline The deadline of the task.
     */
    public Task(String taskTitle, String startTime, String endTime, String description, String deadline) {
        super(taskTitle, startTime, endTime, description);
        this.deadline = deadline;
        this.setType("Task");
    }

    /**
     * Returns the deadline of the task.
     * @return The deadline of the task.
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Returns the type of the CalendarEntity.
     * @return The type of the CalendarEntity.
     */
    @Override
    public String getType() {
        return "Task";
    }

    /**
     * Checks if the task is reschedulable by calling the checkIfTaskIsReschedulable method of the SchedulerController with this task.
     * @return true if the task is reschedulable, false otherwise.
     */
    @Override
    public boolean isReschedulable() {
        return SchedulerController.checkIfTaskIsReschedulable(this);
    }

    /**
     * Returns the title of the task.
     * @return The title of the task.
     */
    @Override
    public String getEntityTitle() {
        return super.getEntityTitle();
    }

    /**
     * Checks if the task is completed.
     * @return true if the task is completed, false otherwise.
     */
    public boolean isCompleted() {
        return isCompleted;
    }


    /**
     * Sets the deadline of the task.
     * @param deadline The deadline of the task.
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    /**
     * Converts the start date of the task to the format "dd/MM HH:mm".
     * @return The start date of the task in the format "dd/MM HH:mm".
     */
    public String convertStartDate(){
        String startDate = super.getStartTime();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = sdf.parse(startDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM HH:mm");

            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts the start date of the task to the format "dd/MM/yyyy".
     * @return The start date of the task in the format "dd/MM/yyyy".
     */
    public String getDate(){
        String startDate = super.getStartTime();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = sdf.parse(startDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts the start time of the task to the format "HH:mm".
     * @return The start time of the task in the format "HH:mm".
     */
    public String getTime(){
        String startDate = super.getStartTime();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = sdf.parse(startDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}