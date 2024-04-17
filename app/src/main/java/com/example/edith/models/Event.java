package com.example.edith.models;

public class Event extends CalendarEntity{
    public Event() {
        super();
    }
    public Event(String taskTitle, String startTime, String endTime, String description) {
        super(taskTitle, startTime, endTime, description);
        this.setType("Event");
    }
}
