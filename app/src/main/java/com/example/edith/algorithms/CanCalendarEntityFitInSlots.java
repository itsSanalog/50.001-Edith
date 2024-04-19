package com.example.edith.algorithms;

import com.example.edith.models.CalendarEntities.CalendarEntity;
import com.example.edith.models.TimeSlot;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class provides a method to check if a CalendarEntity can fit into any of the available TimeSlots.
 */
public class CanCalendarEntityFitInSlots {

    /**
     * Checks if a CalendarEntity can fit into any of the available TimeSlots.
     *
     * @param availableSlots A list of available TimeSlots.
     * @param calendarEntity The CalendarEntity to check.
     * @return true if the CalendarEntity can fit into any of the TimeSlots, false otherwise.
     */
    public static boolean canFit(List<TimeSlot> availableSlots, CalendarEntity calendarEntity){
        LocalDateTime calendarEntityStart = LocalDateTime.parse(calendarEntity.getStartTime());
        LocalDateTime calendarEntityEnd = LocalDateTime.parse(calendarEntity.getEndTime());

        return availableSlots.stream().anyMatch(slot -> {
            LocalDateTime slotStart = LocalDateTime.parse(slot.getStartTime());
            LocalDateTime slotEnd = LocalDateTime.parse(slot.getEndTime());
            return calendarEntityStart.isAfter(slotStart) && calendarEntityEnd.isBefore(slotEnd);
        });
    }
}