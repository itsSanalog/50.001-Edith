package com.example.edith.controllers;

import com.example.edith.models.CalendarEntity;
import com.example.edith.models.TaskRequest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;

public class TaskController {
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //On create task request
    //Send task request to scheduler controller
    TaskRequest taskRequest = new TaskRequest("Entity", df.format(Instant.now().plusSeconds(60*60)), 60);
    //Get available slot from scheduler controller
    ArrayList<CalendarEntity> entitiesToSchedule = new SchedulerController().createTaskRequest(taskRequest);

    public TaskController() throws ParseException {
    }

    //Create task entity
    //Add task entity to local entity storage
    //Update other entities (if necessary)
    //Update the calendar view
    public void rescheduleTask() {

    }
}
