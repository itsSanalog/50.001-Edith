package com.example.edith.algorithms;

import com.example.edith.models.CalendarEntities.CalendarEntity;
import com.example.edith.models.TimeSlot;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods to find available slots in a calendar.
 */
public class FindAvailableSlots {

    /**
     * Returns a list of available slots in the calendar.
     *
     * @param existingEntities A list of existing calendar entities.
     * @param duration The duration of the slot to find.
     * @param deadline The deadline for the slot.
     * @return A list of available slots.
     */
    public static List<TimeSlot> getAvailableSlots(List<CalendarEntity> existingEntities, int duration, String deadline) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime deadlineLDT = LocalDateTime.parse(deadline);

        List<TimeSlot> availableSlots = new ArrayList<>();
        while (currentTime.plusMinutes(duration).isBefore(deadlineLDT)) {
            TimeSlot potentialSlot = new TimeSlot(currentTime.toString(), duration);
            if (isSlotAvailable(potentialSlot, existingEntities)) {
                availableSlots.add(potentialSlot);
            }
            currentTime = currentTime.plusMinutes(duration);
        }

        return combineAdjacentSlots(availableSlots);
    }

    /**
     * Checks if a potential slot is available.
     *
     * @param potentialSlot The potential slot to check.
     * @param existingEntities A list of existing calendar entities.
     * @return true if the slot is available, false otherwise.
     */
    private static boolean isSlotAvailable(TimeSlot potentialSlot, List<CalendarEntity> existingEntities) {
        LocalDateTime potentialSlotStart = LocalDateTime.parse(potentialSlot.getStartTime());
        LocalDateTime potentialSlotEnd = LocalDateTime.parse(potentialSlot.getEndTime());

        return existingEntities.stream().noneMatch(entity -> {
            LocalDateTime entityStart = LocalDateTime.parse(entity.getStartTime());
            LocalDateTime entityEnd = LocalDateTime.parse(entity.getEndTime());
            return entityStart.isBefore(potentialSlotEnd) && entityEnd.isAfter(potentialSlotStart);
        });
    }

    /**
     * Combines adjacent slots into a single slot.
     *
     * @param slots A list of slots.
     * @return A list of combined slots.
     */
    private static List<TimeSlot> combineAdjacentSlots(List<TimeSlot> slots) {
        List<TimeSlot> combinedSlots = new ArrayList<>();
        for (TimeSlot slot : slots) {
            if (!combinedSlots.isEmpty() && combinedSlots.get(combinedSlots.size() - 1).getEndTime().equals(slot.getStartTime())) {
                combinedSlots.get(combinedSlots.size() - 1).setEndTime(slot.getEndTime());
            } else {
                combinedSlots.add(slot);
            }
        }
        return combinedSlots;
    }
}