package com.example.edith.controllers;

import com.example.edith.data.DatabaseOperations;
import com.example.edith.data.FirebaseOperations;
import com.example.edith.data.GoogleCalendarOperations;
import com.example.edith.models.CalendarEntities.Task;
import com.example.edith.models.TaskRequests.addTaskRequest;
import com.example.edith.models.TaskRequests.deleteTaskRequest;
import com.example.edith.models.TaskRequests.updateTaskRequest;

import java.util.List;

/**
 * This class is responsible for managing tasks.
 */
public class TaskController {
    /**
     * Default constructor.
     * Sets Firebase as the database operations instance.
     */
    public TaskController() {
        DatabaseOperations databaseOperations = FirebaseOperations.getInstance();
    }

    /**
     * Adds a task based on the provided task request.
     * It sends the task request to the SchedulerController, gets the available slot,
     * and updates the entities in Firebase.
     *
     * @param addTaskRequest The task request object containing the details of the task.
     */
    public static void addTask(addTaskRequest addTaskRequest) {
        List<Task> entitiesToBeUpdated = SchedulerController.addTaskRequest(addTaskRequest);
        for (Task task : entitiesToBeUpdated) {
            FirebaseOperations.getInstance().addTask(task);
        }
    }

    /**
     * Updates a task based on the provided task request.
     * It deletes the existing task and adds a new task with the updated details.
     *
     * @param updateTaskRequest The task request object containing the updated details of the task.
     */
    public static void updateTask(updateTaskRequest updateTaskRequest) {
        // Get details from updateTaskRequest
        String id = updateTaskRequest.getId();
        String entityName = updateTaskRequest.getEntityName();
        String entityDescription = updateTaskRequest.getEntityDescription();
        String taskDeadline = updateTaskRequest.getTaskDeadline();
        int duration = updateTaskRequest.getDuration();

        // Create deleteTaskRequest and send to TaskController
        deleteTaskRequest deleteTaskRequest = new deleteTaskRequest(id);
        TaskController.deleteTask(deleteTaskRequest);
        // Create addTaskRequest and send to TaskController
        addTaskRequest addTaskRequest = new addTaskRequest(entityName, entityDescription, taskDeadline, duration);
        TaskController.addTask(addTaskRequest);
    }

    /**
     * Deletes a task based on the provided task request.
     * It removes the task from Firebase and Google Calendar.
     *
     * @param deleteTaskRequest The task request object containing the ID of the task to be deleted.
     */
    public static void deleteTask(deleteTaskRequest deleteTaskRequest) {
        FirebaseOperations.getInstance().removeTask(deleteTaskRequest.getId());
        GoogleCalendarOperations.getInstance().deleteCalendarEntity(deleteTaskRequest.getId());
    }
}