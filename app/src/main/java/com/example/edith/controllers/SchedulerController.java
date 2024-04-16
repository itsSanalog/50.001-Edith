package com.example.edith.controllers;

import android.util.Log;

import com.example.edith.algorithms.CanCalendarEntityFitInSlots;
import com.example.edith.algorithms.FindAvailableSlots;
import com.example.edith.algorithms.FindFirstSlotForTask;
import com.example.edith.algorithms.FindRescheduleSlotForTask;
import com.example.edith.data.DatabaseOperations;
import com.example.edith.data.FirebaseOperations;
import com.example.edith.models.CalendarEntity;
import com.example.edith.models.Task;
import com.example.edith.models.TaskRequests.TaskRequest;
import com.example.edith.models.TimeSlot;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class SchedulerController {

    public SchedulerController() {
    }

    /**
     * This method is responsible for processing a task request and finding the first available slot for the task.
     * It uses the FindAvailableSlots and FindFirstSlotForTask algorithms to achieve this.
     *
     * @param taskRequest The task request object containing the details of the task such as its duration and deadline.
     * @return An ArrayList of CalendarEntity objects representing the rescheduled entities after the task has been scheduled.
     */
    public static List<Task> addTaskRequest(TaskRequest taskRequest) {
        DatabaseOperations databaseOperations = FirebaseOperations.getInstance();
        // Get the deadline from the task request
        String deadline = taskRequest.getDeadline();
        // Get the duration from the task request
        int duration = taskRequest.getDuration();
        String description = taskRequest.getDescription();
        Log.d("GoogleCalendarOperations", "Duration: " + duration + " Deadline: " + deadline);
        // Get the existing entities from the local storage
        List<Task> existingTasks = databaseOperations.getAllTasks();
        List<CalendarEntity> existingEntities = new ArrayList<>(existingTasks);
        Log.d("GoogleCalendarOperations", "Existing entities: " + existingEntities.size());
        // Use the FindAvailableSlots algorithm to find all available slots that can accommodate the task
        List<TimeSlot> availableSlots = FindAvailableSlots.getAvailableSlots(existingEntities, duration, deadline);
        Log.d("GoogleCalendarOperations", "Available slots have size: " + availableSlots.size());
        Log.d("GoogleCalendarOperations", "Available slots: " + availableSlots);
        //Log.d("GoogleCalendarOperations", "Available slots: " + availableSlots.get(0).getStartTime()+ " - " + availableSlots.get(0).getEndTime());
        // Create an instance of FindFirstSlotForTask
        return FindFirstSlotForTask.find(availableSlots, duration, deadline, taskRequest.getEntityName(), existingEntities, description);
        // Use the FindFirstSlotForTask algorithm to find the first available slot for the task and return the rescheduled entities
    }
    public static Task rescheduleTaskRequest(Task task) {
        DatabaseOperations databaseOperations = FirebaseOperations.getInstance();
        if (checkIfTaskIsReschedulable(task)) {
            // Get the deadline from the task request
            String deadline = task.getDeadline();
            // Get the duration from the task request
            int duration = task.getDurationMinutes();
            // Get the existing entities from the local storage
            List<CalendarEntity> existingEntities = databaseOperations.getAllCalendarEntities();
            // Use the FindAvailableSlots algorithm to find all available slots that can accommodate the task
            // This excludes the existing slot of the task to be rescheduled
            List<TimeSlot> availableSlots = FindAvailableSlots.getAvailableSlots(existingEntities, duration, deadline);
            // Create an instance of FindFirstSlotForTask
            return FindRescheduleSlotForTask.find(availableSlots, task.getEntityTitle(), task);
        }
        return null;
    }
    public static boolean checkIfTaskIsReschedulable(Task task) {
        DatabaseOperations databaseOperations = FirebaseOperations.getInstance();
        String deadline = task.getDeadline();
        int duration = task.getDurationMinutes();
        List<CalendarEntity> existingEntities = databaseOperations.getAllCalendarEntities();
        List<TimeSlot> availableSlots = FindAvailableSlots.getAvailableSlots(existingEntities, duration, deadline);
        return CanCalendarEntityFitInSlots.canFit(availableSlots, task);
    }


}
