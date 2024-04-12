package com.example.edith.models;

import java.time.ZonedDateTime;
import java.util.UUID;

public class CalendarEntity {
    private final String entityID;
    private ZonedDateTime createdDateTime; //Mandatory
    private String entityTitle; //Mandatory
    private int durationMinutes; //Mandatory
    private TimeSlot timeSlot; //Mandatory
    private String description; //Optional
    //High=1, Medium=2, Low=3
    private int priority; //Optional

    boolean isScheduled;
    boolean isStartDateTimeInFuture() {
        //Code out logic
        return true;
    }
    public CalendarEntity(String entityTitle, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.createdDateTime = ZonedDateTime.now();
        this.entityTitle = entityTitle;
        this.timeSlot = new TimeSlot(startTime,endTime);
        this.entityID = UUID.randomUUID().toString();
    }

    public void deleteCalendarEntity() {
        //Figure out how to delete from database
    }
    //Getter methods to access
    public ZonedDateTime getStartTime() {
        return timeSlot.getStartTime();
    }
    public ZonedDateTime getEndTime() {
        return timeSlot.getEndTime();
    }

    public String getEntityTitle() {
        return entityTitle;
    }

    public void setEndTime(ZonedDateTime zonedDateTime) {
        timeSlot.setEndTime(zonedDateTime);
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
    public void setStartTime(ZonedDateTime startTime) {
        timeSlot.setStartTime(startTime);
    }

    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
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

    public void setCreatedDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
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
