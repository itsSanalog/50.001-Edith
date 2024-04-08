package com.example.edith.algorithms;

import com.example.edith.models.CalendarEntity;
import com.example.edith.models.TimeSlot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;

public class FindAvailableSlots {
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FindAvailableSlots() {
    }

    /**
     * This method finds and returns all available time slots.
     * It iterates over the time from now until the deadline, checking for availability of each potential slot.
     * It also combines adjacent slots into one.
     *
     * @return An ArrayList of TimeSlot objects representing all available time slots.
     */
    public static ArrayList<TimeSlot> getAvailableSlots(ArrayList<CalendarEntity> existingEntities, long duration, String deadline) throws ParseException {
        ArrayList<TimeSlot> availableSlots = new ArrayList<>();
        Instant currentTime = Instant.now();

        while (currentTime.plusSeconds(duration*60).isBefore(Instant.ofEpochSecond(df.parse(deadline).getTime()))) {
            TimeSlot potentialSlot = new TimeSlot(df.format(currentTime), duration);

            if (isSlotAvailable(potentialSlot, existingEntities)) {
                availableSlots.add(potentialSlot);
            }

            currentTime = currentTime.plusSeconds(duration*60);
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
    public static boolean isSlotAvailable(TimeSlot potentialSlot, ArrayList<CalendarEntity> existingEntities) {
        for (CalendarEntity existingEntity : existingEntities) {
            if (existingEntity.getTimeSlot().startTimeIsBeforeTimeslot(potentialSlot) &&
                    existingEntity.getTimeSlot().startTimeIsAfterTimeslot(potentialSlot)) {
                return false;
            }
        }
        return true;
    }
}