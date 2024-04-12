package com.example.edith.controllers;

import com.example.edith.models.CalendarEntity;
import com.example.edith.models.TaskRequest;
import com.example.edith.models.addTaskRequest;
import com.example.edith.models.deleteTaskRequest;
import com.example.edith.models.updateTaskRequest;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class TaskController () {
    //On create task request
    //Send task request to scheduler controller
    //Get available slot from scheduler controller
    //Create task entity
    //Add task entity to local entity storage
    //Update other entities (if necessary)
    //Update the calendar view

    // Add taskRequest to be passed into here.
    public void addTask(addTaskRequest addTaskRequest) {

    }

    // Update taskRequest to be passed into here.
    public void updateTask(String id, updateTaskRequest updateTaskRequest) {

    }

    public void deleteTask(deleteTaskRequest deleteTaskRequest) {

    }

}
