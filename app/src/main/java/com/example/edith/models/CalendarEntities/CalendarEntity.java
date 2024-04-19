package com.example.edith.models.CalendarEntities;

import com.example.edith.models.TimeSlot;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * CalendarEntity is a class that represents a calendar entity with an entity ID, entity title, duration in minutes, time slot, description, priority, update requirement, type, and scheduling status.
 * It provides constructors to initialize these values and methods to get and set these values.
 */
public class CalendarEntity {
    private final String entityID;
    private String entityTitle;
    private int durationMinutes;
    private TimeSlot timeSlot;
    private String description;
    private int priority;
    private boolean updateRequired;
    private String type;
    private boolean isScheduled;


    /**
     * Default constructor for CalendarEntity.
     * Initializes the entity ID with a random UUID and sets updateRequired to true.
     */
    public CalendarEntity() {
        this.entityTitle = null;
        this.timeSlot = null;
        this.entityID = UUID.randomUUID().toString();
        this.description = description;
        this.updateRequired = true;
    }

    /**
     * Constructor for CalendarEntity.
     * Initializes the entity title, time slot, entity ID, description, update requirement, and type with the provided values.
     * @param entityTitle The title of the entity.
     * @param startTime The start time of the time slot.
     * @param endTime The end time of the time slot.
     * @param description The description of the entity.
     */
    public CalendarEntity(String entityTitle, String startTime, String endTime, String description) {
        this.entityTitle = entityTitle;
        this.timeSlot = new TimeSlot(startTime,endTime);
        this.entityID = UUID.randomUUID().toString();
        this.description = description;
        this.updateRequired = true;
        this.type = "CalendarEntity";
    }

    /**
     * Constructor for CalendarEntity.
     * Initializes the entity title, time slot, entity ID, description, update requirement, and type with the provided values.
     * @param entityTitle The title of the entity.
     * @param startTime The start time of the time slot.
     * @param endTime The end time of the time slot.
     * @param description The description of the entity.
     * @param ID The ID of the entity.
     */
    public CalendarEntity(String entityTitle, String startTime, String endTime, String description, String ID) {
        this.entityTitle = entityTitle;
        this.timeSlot = new TimeSlot(startTime,endTime);
        this.entityID = ID;
        this.description = description;
        this.updateRequired = true;
        this.type = "CalendarEntity";
    }

    // Getter methods to access the values

    public String getStartTime() {
        return timeSlot.getStartTime();
    }

    public String getEndTime() {
        return timeSlot.getEndTime();
    }

    public String getEntityTitle() {
        return entityTitle;
    }

    // Setter methods to update the values

    public void setEndTime(String endTime) {
        timeSlot.setEndTime(endTime);
    }

    /**
     * Checks if the entity is reschedulable.
     * @return false by default.
     */
    public boolean isReschedulable() {
        //Not reschedulable by default
        return false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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