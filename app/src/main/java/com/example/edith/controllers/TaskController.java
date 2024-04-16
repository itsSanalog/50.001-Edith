package com.example.edith.controllers;

import android.util.Log;

import com.example.edith.data.DatabaseOperations;
import com.example.edith.data.FirebaseOperations;
import com.example.edith.models.CalendarEntity;
import com.example.edith.models.Task;
import com.example.edith.models.TaskRequests.addTaskRequest;
import com.example.edith.models.TaskRequests.deleteTaskRequest;
import com.example.edith.models.TaskRequests.updateTaskRequest;

import java.util.ArrayList;
import java.util.List;

public class TaskController {


    public TaskController() {
        // Set Firebase as database operations
        DatabaseOperations databaseOperations = FirebaseOperations.getInstance();
    }

    // Add taskRequest to be passed into here.
    public static void addTask(addTaskRequest addTaskRequest) {
        //Send task request to scheduler controller
        //Get available slot from scheduler controller
        List<Task> entitiesToBeUpdated = SchedulerController.addTaskRequest(addTaskRequest);
        //Update entities in firebase
        Log.d("GoogleCalendarOperations", "Entities to be updated in taskcontroller: " + entitiesToBeUpdated.size());

        for (Task task : entitiesToBeUpdated) {
            //Update entity in firebase
            FirebaseOperations.getInstance().addTask(task);
            Log.d("GoogleCalendarOperations", "Task added to firebase: " + task.getEntityTitle());
        }
    }

    // Update taskRequest to be passed into here.
    public static void updateTask(updateTaskRequest updateTaskRequest) {

    }

    public static void deleteTask(deleteTaskRequest deleteTaskRequest) {
        FirebaseOperations.getInstance().removeTask(deleteTaskRequest.getId());
        Log.d("TaskController", deleteTaskRequest.getId());
    }
}
