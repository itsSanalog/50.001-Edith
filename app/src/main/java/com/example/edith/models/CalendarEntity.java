package com.example.edith.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.UUID;
import java.util.zip.DataFormatException;

public class CalendarEntity {
    private final String entityID;
    private long createdDateTime; //Mandatory
    private String entityTitle; //Mandatory
    private int durationMinutes; //Mandatory
    private TimeSlot timeSlot; //Mandatory
    private String description; //Optional
    //High=1, Medium=2, Low=3
    private int priority; //Optional
    boolean isScheduled;

    public boolean isStartDateTimeInFuture() {
        //Code out logic
        return true;
    }
    public boolean isScheduled() {
        return isScheduled;
    }
    public boolean isReschedulable() throws ParseException {
        //Not reschedulable by default
        return false;
    }

    public CalendarEntity() {
        this.entityID = UUID.randomUUID().toString();
    };
    public CalendarEntity(String entityTitle, String startTime, String endTime) {
        this.createdDateTime = Instant.now().getEpochSecond();
        this.entityTitle = entityTitle;
        this.timeSlot = new TimeSlot(startTime,endTime);
        this.entityID = UUID.randomUUID().toString();
    }

    //Getter methods
    public String getStartTime() {
        return timeSlot.getStartTime();
    }
    public String getEndTime() {
        return timeSlot.getEndTime();
    }

    public String getEntityTitle() {
        return entityTitle;
    }

    public void setEndTime(String dateTime) {
        timeSlot.setEndTime(dateTime);
    }
    public int getDurationMinutes() {
        return (int) timeSlot.getDuration();
    }
    public String getCreatedDateTime() {
        return Instant.ofEpochSecond(createdDateTime).toString();
    }
    public String getDescription() {
        return description;
    }
    public int getPriority() {
        return priority;
    }
    public String getEntityID() {
        return entityID;
    }
    public String getType() {
        return "Entity";
    }
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    //Setter methods to update
    public void setStartTime(String startTime) {
        timeSlot.setStartTime(startTime);
    }
    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = Instant.parse(createdDateTime).getEpochSecond();
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
    public void setEntityTitle(String title) {
        this.entityTitle = title;
    }


}