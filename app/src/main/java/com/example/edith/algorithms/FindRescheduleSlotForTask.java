package com.example.edith.algorithms;

import com.example.edith.models.CalendarEntities.Task;
import com.example.edith.models.TimeSlot;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This class is used to find a reschedule slot for a task in a calendar.
 * It takes into account available time slots, the desired duration of the task, and a deadline.
 */
public class FindRescheduleSlotForTask {

    public FindRescheduleSlotForTask() {
    }

    /**
     * This method finds a new slot for the task and sets the new start and end times for this task.
     * It iterates over the available slots and finds the first one that can accommodate the task.
     * It then updates the start and end times of the task.
     *
     * @return The task with updated start and end times. If no suitable slot is found, it returns null.
     */
    public static Task find(List<TimeSlot> availableSlots, String name, Task task) {
        long duration = task.getDurationMinutes();
        for (TimeSlot slot : availableSlots) {
            if (slot.getDuration() >= duration) {
                String startTime = slot.getStartTime();
                String endTime = LocalDateTime.parse(startTime).plusMinutes(duration).toString();
                task.setStartTime(startTime);
                task.setEndTime(endTime);
                return task;
            }
        }
        return null;
    }
}