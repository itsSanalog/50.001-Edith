package com.example.edith.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class TimeSlot {
    private String startTime;
    private String endTime;
    private int duration; //In minutes

    public TimeSlot() {
        this.startTime = null;
        this.endTime = null;
    }
    public TimeSlot(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public TimeSlot(String startTime, int duration) {
        this.startTime = startTime;
        this.endTime = LocalDateTime.parse(startTime).plusMinutes(duration).toString();
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public int getDuration() {
        return (int) Duration.between(LocalDateTime.parse(startTime),LocalDateTime.parse(endTime)).toMinutes();
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
