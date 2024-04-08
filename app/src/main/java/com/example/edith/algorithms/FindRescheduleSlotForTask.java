package com.example.edith.algorithms;

import com.example.edith.models.Task;
import com.example.edith.models.TimeSlot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * This class is used to find a reschedule slot for a task in a calendar.
 * It takes into account available time slots, the desired duration of the task, and a deadline.
 */
public class FindRescheduleSlotForTask {
    static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FindRescheduleSlotForTask() {
    }

    /**
     * This method finds a new slot for the task and sets the new start and end times for this task.
     * It iterates over the available slots and finds the first one that can accommodate the task.
     * It then updates the start and end times of the task.
     *
     * @return The task with updated start and end times. If no suitable slot is found, it returns null.
     */
    public static Task find(ArrayList<TimeSlot> availableSlots, String name, Task task) {
        long duration = task.getDurationMinutes();
        for (TimeSlot slot : availableSlots) {
            if (slot.getDuration() >= duration) {
                String startTime = slot.getStartTime();
                String endTime = null;
                try {
                    endTime = df.format(df.parse(startTime).getTime()+duration*60);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                task.setStartTime(startTime);
                task.setEndTime(endTime);
                return task;
            }
        }
        return null;
    }
}