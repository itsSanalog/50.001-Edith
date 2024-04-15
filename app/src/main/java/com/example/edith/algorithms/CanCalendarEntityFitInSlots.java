package com.example.edith.algorithms;

import com.example.edith.models.CalendarEntity;
import com.example.edith.models.TimeSlot;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CanCalendarEntityFitInSlots {
    ArrayList<TimeSlot> availableSlots;
    CalendarEntity calendarEntity;
    public CanCalendarEntityFitInSlots(){
    }
    public static boolean canFit(ArrayList<TimeSlot> availableSlots, CalendarEntity calendarEntity){
        boolean canFit = false;
        for (TimeSlot slot : availableSlots) {
            LocalDateTime calendarEntityStart = LocalDateTime.parse(calendarEntity.getStartTime());
            LocalDateTime calendarEntityEnd = LocalDateTime.parse(calendarEntity.getEndTime());
            LocalDateTime slotStart = LocalDateTime.parse(slot.getStartTime());
            LocalDateTime slotEnd = LocalDateTime.parse(slot.getEndTime());
            if (calendarEntityStart.isAfter(slotStart) && calendarEntityEnd.isBefore(slotEnd)) {
                canFit = true;
                break;
            }
        }
        return canFit;
    }

}
