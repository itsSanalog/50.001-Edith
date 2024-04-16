package com.example.edith.algorithms;

import com.example.edith.data.FirebaseOperations;
import com.example.edith.models.CalendarEntity;
import com.example.edith.models.Task;
import com.example.edith.models.TimeSlot;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class FindAvailableSlots {

    public FindAvailableSlots() {
    }

    /**
     * This method finds and returns all available time slots.
     * It iterates over the time from now until the deadline, checking for availability of each potential slot.
     * It also combines adjacent slots into one.
     *
     * @return An ArrayList of TimeSlot objects representing all available time slots.
     */
    public static List<TimeSlot> getAvailableSlots(List<CalendarEntity> existingEntities, int duration, String deadline) {
        ArrayList<TimeSlot> availableSlots = new ArrayList<>();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime deadlineLDT = LocalDateTime.parse(deadline);

        while (currentTime.plusMinutes(duration).isBefore(deadlineLDT)) {
            TimeSlot potentialSlot = new TimeSlot(currentTime.toString(), duration);

            if (isSlotAvailable(potentialSlot)) {
                availableSlots.add(potentialSlot);
            }

            currentTime = currentTime.plusMinutes(duration);
        }

        // Combine adjacent slots
        ArrayList<TimeSlot> combinedSlots = new ArrayList<>();
        for (TimeSlot slot : availableSlots) {
            if (!combinedSlots.isEmpty()) {
                TimeSlot lastSlot = combinedSlots.get(combinedSlots.size() - 1);
                if (lastSlot.getEndTime().equals(slot.getStartTime())) {
                    // If the end time of the last slot is equal to the start time of the current slot,
                    // update the end time of the last slot to the end time of the current slot.
                    lastSlot.setEndTime(slot.getEndTime());
                    continue;
                }
            }
            combinedSlots.add(slot);
        }

        return combinedSlots;
    }

    /**
     * This method checks if a potential time slot is available.
     * It checks if the potential slot overlaps with any existing event.
     *
     * @param potentialSlot The potential time slot to check.
     * @return A boolean value indicating whether the potential slot is available.
     */
    public static boolean isSlotAvailable(TimeSlot potentialSlot) {
        List<Task> existingTasks = FirebaseOperations.getInstance().getAllTasks();
        List<CalendarEntity> existingEntities = new ArrayList<>(existingTasks);
        for (CalendarEntity existingEntity : existingEntities) {
            LocalDateTime existingEntityStart = LocalDateTime.parse(existingEntity.getStartTime());
            LocalDateTime existingEntityEnd = LocalDateTime.parse(existingEntity.getEndTime());
            LocalDateTime potentialSlotStart = LocalDateTime.parse(potentialSlot.getStartTime());
            LocalDateTime potentialSlotEnd = LocalDateTime.parse(potentialSlot.getEndTime());
            if (existingEntityStart.isBefore(potentialSlotEnd) &&
                    existingEntityEnd.isAfter(potentialSlotStart)) {
                return false;
            }
        }
        return true;
    }
}