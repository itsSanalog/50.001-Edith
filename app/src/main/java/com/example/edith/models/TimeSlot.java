package com.example.edith.models;

import java.time.Duration;
import java.time.ZonedDateTime;

public class TimeSlot {
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private int duration; //In minutes
    public TimeSlot(ZonedDateTime startTime, ZonedDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public TimeSlot(ZonedDateTime startTime, int duration) {
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(duration);
    }
    public ZonedDateTime getStartTime() {
        return startTime;
    }
    public ZonedDateTime getEndTime() {
        return endTime;
    }
    public int getDuration() {
        return (int) Duration.between(startTime,endTime).toMinutes();
    }
    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

}
