package com.example.edith.models;

import com.example.edith.controllers.SchedulerController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.ZonedDateTime;

public class Task extends CalendarEntity {
    private boolean isCompleted;
    private boolean isOverdue;
    private String deadline;
    public Task() {
        super(null,null,null);
    }

    public Task(String taskTitle, String startTime, String endTime) {
        super(taskTitle, startTime, endTime);
    }
    public String getDeadline() {
        return deadline;
    }
    @Override
    public String getType() {
        return "Task";
    }
    @Override
    public boolean isReschedulable() {
        return SchedulerController.checkIfTaskIsReschedulable(this);
    }
    @Override
    public String getEntityTitle() {
        return super.getEntityTitle();
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setOverdue(boolean isOverdue) {
        this.isOverdue = isOverdue;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String convertStartDate(){
        String startDate = super.getStartTime();

        try {
            // Create a SimpleDateFormat object with the desired date format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            // Parse the input string to a Date object
            Date date = sdf.parse(startDate);
            // Create a SimpleDateFormat object with the desired output format
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM HH:mm");
            // Format the Date object to a string with the desired output format
            String output = outputFormat.format(date);

            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDate(){
        String startDate = super.getStartTime();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = sdf.parse(startDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            String output = outputFormat.format(date);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTime(){
        String startDate = super.getStartTime();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = sdf.parse(startDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
            String output = outputFormat.format(date);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
