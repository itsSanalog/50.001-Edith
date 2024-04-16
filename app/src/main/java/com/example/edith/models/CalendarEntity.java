package com.example.edith.models;

import java.time.ZonedDateTime;
import java.util.UUID;

public class CalendarEntity {
    private final String entityID;
    private String entityTitle; //Mandatory
    private int durationMinutes; //Mandatory
    private TimeSlot timeSlot; //Mandatory
    private String description; //Optional
    //High=1, Medium=2, Low=3
    private int priority; //Optional
    private boolean updateRequired;


    boolean isScheduled;
    boolean isStartDateTimeInFuture() {
        //Code out logic
        return true;
    }
    public CalendarEntity() {
        this.entityTitle = null;
        this.timeSlot = null;
        this.entityID = UUID.randomUUID().toString();
        this.updateRequired = true;
    }
    public CalendarEntity(String entityTitle, String startTime, String endTime, String description) {
        this.entityTitle = entityTitle;
        this.timeSlot = new TimeSlot(startTime,endTime);
        this.entityID = UUID.randomUUID().toString();
        this.description = description;
        this.updateRequired = true;
    }

    public void deleteCalendarEntity() {
        //Figure out how to delete from database
    }
    //Getter methods to access
    public String getStartTime() {
        return timeSlot.getStartTime();
    }
    public String getEndTime() {
        return timeSlot.getEndTime();
    }
    public String getEntityTitle() {
        return entityTitle;
    }

    public void setEndTime(String endTime) {
        timeSlot.setEndTime(endTime);
    }
    //Setter methods to update
    public boolean isReschedulable() {
        //Not reschedulable by default
        return false;
    }
    public String getType() {
        return "Entity";
    }
    public int getDurationMinutes() {
        return (int) timeSlot.getDuration();
    }
    public void setStartTime(String startTime) {
        timeSlot.setStartTime(startTime);
    }

    public void setUpdateRequired(boolean updateRequired) {
        this.updateRequired = updateRequired;
    }
    public boolean getUpdateRequired() {
        return updateRequired;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
    public boolean isScheduled() {
        return isScheduled;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setScheduled(boolean isScheduled) {
        this.isScheduled = isScheduled;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityTitle(String title) {
        this.entityTitle = title;
    }

    public TimeSlot getTimeSlot(){
        return timeSlot;
    }

}
