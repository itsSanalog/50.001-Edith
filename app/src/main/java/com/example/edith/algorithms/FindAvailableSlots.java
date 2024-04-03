package com.example.edith.algorithms;

import com.example.edith.models.CalendarEntity;
import com.example.edith.models.TimeSlot;

import java.time.ZonedDateTime;
import java.util.ArrayList;

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
    public static ArrayList<TimeSlot> getAvailableSlots(ArrayList<CalendarEntity> existingEntities, int duration, ZonedDateTime deadline) {
        ArrayList<TimeSlot> availableSlots = new ArrayList<>();
        ZonedDateTime currentTime = ZonedDateTime.now();

        while (currentTime.plusMinutes(duration).isBefore(deadline)) {
            TimeSlot potentialSlot = new TimeSlot(currentTime, duration);

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
        ArrayList<CalendarEntity> existingEntities = new ArrayList<>(); // Take from LES
        for (CalendarEntity existingEntity : existingEntities) {
            if (existingEntity.getStartTime().isBefore(potentialSlot.getEndTime()) &&
                    existingEntity.getEndTime().isAfter(potentialSlot.getStartTime())) {
                return false;
            }
        }
        return true;
    }
}