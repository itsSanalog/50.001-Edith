package com.example.edith.algorithms;

import android.util.Log;

import com.example.edith.controllers.SchedulerController;
import com.example.edith.data.FirebaseOperations;
import com.example.edith.models.CalendarEntity;
import com.example.edith.models.Task;
import com.example.edith.models.TimeSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class FindFirstSlotForTask {
    public FindFirstSlotForTask() {
    }

    // Given a list of available time slots, a task duration, a deadline, a task name, and a list of calendar entities,
    // this method finds the first available time slot that can accommodate the task.
    // If no available slot exists, it attempts to rearrange the existing tasks to make space for the new task.
    // It returns an ArrayList of Task objects representing the rescheduled tasks after the new task has been scheduled.
    public static List<Task> find(List<TimeSlot> availableSlots, int duration, String deadline, String taskTitle, List<CalendarEntity> calendarEntities, String description) {
        LocalDateTime deadlineLDT = LocalDateTime.parse(deadline);
        LocalDateTime now = LocalDateTime.now();
        Log.d("GoogleCalendarOperations", "Localtime now" + now);
        if (availableSlots == null) {
            return null;
        }
        ArrayList<Task> rescheduledTasks = new ArrayList<>();
        if (!availableSlots.isEmpty()) {
            for (int i = 0; i < availableSlots.size(); i++) {
                TimeSlot availableSlot = availableSlots.get(i);
                if (duration <= availableSlot.getDuration()) {
                    LocalDateTime availableSlotStart = LocalDateTime.parse(availableSlot.getStartTime());
                    Task task = new Task(taskTitle, availableSlot.getStartTime(), availableSlotStart.plusMinutes(duration).toString(), description, deadline);
                    rescheduledTasks.add(task);
                    Log.d("GoogleCalendarOperations", "Task added to rescheduled tasks: " + task.getEntityTitle() + " " + task.getStartTime() + " " + task.getEndTime());
                    return rescheduledTasks;
                }
            }
        }
        //If no available slot exists
        //Attempt to rearrange the existing tasks to make space for the new task

        ArrayList<Task> holdEntities = new ArrayList<>();

        for (Task task : FirebaseOperations.getInstance().getAllTasks()) {
            if (task.getType().equals("Task")) {
                holdEntities.add(task);
                calendarEntities.remove(task);
            }
        }


        List<TimeSlot> availableSlots2 = FindAvailableSlots.getAvailableSlots(calendarEntities, duration, deadline);
        holdEntities.sort((e1, e2) -> Long.compare(e2.getDurationMinutes(), e1.getDurationMinutes()));
        boolean isPlaced = false;
        for (Task holdEntity : holdEntities) {
            Log.d("rescheduleee" , "Entity is being rescheduled" + holdEntity.getEntityTitle());
            LocalDateTime nonTaskEntityStart = LocalDateTime.parse(holdEntity.getStartTime());
            LocalDateTime nonTaskEntityEnd = LocalDateTime.parse(holdEntity.getEndTime());

            if (nonTaskEntityStart.isAfter(now) && nonTaskEntityEnd.isBefore(deadlineLDT)) {
                LocalDateTime proposedEndTimeLDT = nonTaskEntityStart.plusMinutes(holdEntity.getDurationMinutes());
                String proposedEndTime = proposedEndTimeLDT.toString();
                if (proposedEndTimeLDT.isBefore(nonTaskEntityEnd)) {
                    // The holdEntity can be placed in the tempEntity slot
                    holdEntity.setEndTime(proposedEndTime);
                    calendarEntities.add(holdEntity);
                    // Update the start time of the tempEntity to reflect the placement of the holdEntity
                    holdEntity.setStartTime(proposedEndTime);
                    break;
                }
            }

        if (!isPlaced) {
            for (int i = 0; i < holdEntities.size(); i++) {

                if (holdEntities.get(i).getDurationMinutes() < duration) {
                    break;
                }
                if (holdEntities.get(i).isReschedulable()) {
                    LocalDateTime holdEntityStart = LocalDateTime.parse(holdEntities.get(i).getStartTime());
                    int holdEntityDuration = holdEntities.get(i).getDurationMinutes();
                    Task newTask = new Task(taskTitle, holdEntities.get(i).getStartTime(), holdEntityStart.plusMinutes(holdEntityDuration).toString(), deadline, description);
                    rescheduledTasks.add(newTask);
                    rescheduledTasks.add(SchedulerController.rescheduleTaskRequest((Task) holdEntities.get(i)));
                    break;
                }
            }
            Log.d("rescheduleee", "returning null 1");
            return null;
        }
        }
        //Compare finalEntities with calendarEntities
        for (CalendarEntity entity : calendarEntities) {
            if (Objects.equals(entity.getType(), "task")) {
                if (!calendarEntities.contains(entity)) {
                    rescheduledTasks.add((Task) entity);
                }
                if (calendarEntities.contains(entity)) {
                    for (CalendarEntity entity1 : calendarEntities) {
                        if (entity1.equals(entity)) {
                            //If entity in finalEntities has different start time in calendarEntities
                            if (!entity1.getStartTime().equals(entity.getStartTime())) {
                                //Add entity to rescheduledCalendarEntities
                                rescheduledTasks.add((Task) entity);
                            }
                        }
                    }
                }
            }
        }

        Log.d("rescheduleee", "Rescheduled tasks: " + rescheduledTasks.toString());
        return rescheduledTasks;
    }


}
