package com.example.edith.controllers;

import com.example.edith.algorithms.CanCalendarEntityFitInSlots;
import com.example.edith.algorithms.FindAvailableSlots;
import com.example.edith.algorithms.FindFirstSlotForTask;
import com.example.edith.algorithms.FindRescheduleSlotForTask;
import com.example.edith.models.CalendarEntity;
import com.example.edith.models.Task;
import com.example.edith.models.TaskRequest;
import com.example.edith.models.TimeSlot;

import java.time.ZonedDateTime;
import java.util.ArrayList;


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
    public static ArrayList<CalendarEntity> createTaskRequest(TaskRequest taskRequest) {
        // Get the deadline from the task request
        ZonedDateTime deadline = taskRequest.getDeadline();
        // Get the duration from the task request
        int duration = taskRequest.getDuration();
        // Get the existing entities from LocalEntityStorage
        ArrayList<CalendarEntity> existingEntities = new ArrayList<>();

        // Use the FindAvailableSlots algorithm to find all available slots that can accommodate the task
        ArrayList<TimeSlot> availableSlots = FindAvailableSlots.getAvailableSlots(existingEntities, duration, deadline);
        // Create an instance of FindFirstSlotForTask
        return FindFirstSlotForTask.find(availableSlots, duration, deadline, taskRequest.getEntityName(), existingEntities);
        // Use the FindFirstSlotForTask algorithm to find the first available slot for the task and return the rescheduled entities
    }
    public static Task rescheduleTaskRequest(Task task) {
        if (checkIfTaskIsReschedulable(task)) {
            // Get the deadline from the task request
            ZonedDateTime deadline = task.getDeadline();
            // Get the duration from the task request
            int duration = task.getDurationMinutes();
            // Get the existing entities from the local storage
            ArrayList<CalendarEntity> existingEntities = new ArrayList<>(); // Take from LES
            // Use the FindAvailableSlots algorithm to find all available slots that can accommodate the task
            // This excludes the existing slot of the task to be rescheduled
            ArrayList<TimeSlot> availableSlots = FindAvailableSlots.getAvailableSlots(existingEntities, duration, deadline);
            // Create an instance of FindFirstSlotForTask
            return FindRescheduleSlotForTask.find(availableSlots, task.getEntityTitle(), task);
        }
        return null;
    }
    public static boolean checkIfTaskIsReschedulable(Task task) {
        ZonedDateTime deadline = task.getDeadline();
        int duration = task.getDurationMinutes();
        ArrayList<CalendarEntity> existingEntities = new ArrayList<>(); //Take from LES
        ArrayList<TimeSlot> availableSlots = FindAvailableSlots.getAvailableSlots(existingEntities, duration, deadline);
        return CanCalendarEntityFitInSlots.canFit(availableSlots, task);
    }


}
