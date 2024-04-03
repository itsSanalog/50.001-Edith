package com.example.edith.controllers;

import com.blazeit.edith.models.CalendarEntity;
import com.blazeit.edith.models.TaskRequest;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class TaskController {
    //On create task request
    //Send task request to scheduler controller
    TaskRequest taskRequest = new TaskRequest("Entity", ZonedDateTime.now().plusHours(1), 60);
    //Get available slot from scheduler controller
    ArrayList<CalendarEntity> entitiesToSchedule = new SchedulerController().createTaskRequest(taskRequest);
    //Create task entity
    //Add task entity to local entity storage
    //Update other entities (if necessary)
    //Update the calendar view
    public void rescheduleTask() {

    }
}
