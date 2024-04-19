package com.example.edith.models.CalendarEntities;

/**
 * Event is a class that extends CalendarEntity and represents an event.
 * It provides a default constructor and a constructor to initialize the event title, start time, end time, and description.
 */
public class Event extends CalendarEntity {
    /**
     * Default constructor for Event.
     * Calls the default constructor of the superclass CalendarEntity.
     */
    public Event() {
        super();
    }

    /**
     * Constructor for Event.
     * Initializes the event title, start time, end time, and description with the provided values.
     * Sets the type of the CalendarEntity to "Event".
     * @param taskTitle The title of the event.
     * @param startTime The start time of the event.
     * @param endTime The end time of the event.
     * @param description The description of the event.
     */
    public Event(String taskTitle, String startTime, String endTime, String description) {
        super(taskTitle, startTime, endTime, description);
        this.setType("Event");
    }
}