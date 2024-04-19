package com.example.edith.models;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * TimeSlot is a class that represents a time slot with a start time, end time, and duration.
 * It provides methods to get and set the start time, end time, and duration.
 */
public class TimeSlot {
    private String startTime;
    private String endTime;

    /**
     * Default constructor for TimeSlot.
     * Initializes startTime and endTime to null.
     * Required for Firebase object mapping.
     */
    public TimeSlot() {
        this.startTime = null;
        this.endTime = null;
    }

    /**
     * Constructor for TimeSlot.
     * Initializes startTime and endTime with the provided values.
     * @param startTime The start time of the time slot.
     * @param endTime The end time of the time slot.
     */
    public TimeSlot(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructor for TimeSlot.
     * Initializes startTime with the provided value and calculates endTime based on the duration.
     * @param startTime The start time of the time slot.
     * @param duration The duration of the time slot in minutes.
     */
    public TimeSlot(String startTime, int duration) {
        this.startTime = startTime;
        this.endTime = LocalDateTime.parse(startTime).plusMinutes(duration).toString();
    }

    /**
     * Returns the start time of the time slot.
     * @return The start time of the time slot.
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time of the time slot.
     * @return The end time of the time slot.
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Returns the duration of the time slot in minutes.
     * @return The duration of the time slot in minutes.
     */
    public int getDuration() {
        return (int) Duration.between(LocalDateTime.parse(startTime),LocalDateTime.parse(endTime)).toMinutes();
    }

    /**
     * Sets the start time of the time slot.
     * @param startTime The start time to set.
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the end time of the time slot.
     * @param endTime The end time to set.
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}