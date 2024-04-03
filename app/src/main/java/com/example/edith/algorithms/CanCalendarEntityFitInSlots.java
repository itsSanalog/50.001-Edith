package com.example.edith.algorithms;

import com.blazeit.edith.models.CalendarEntity;
import com.blazeit.edith.models.TimeSlot;

import java.util.ArrayList;

public class CanCalendarEntityFitInSlots {
    ArrayList<TimeSlot> availableSlots;
    CalendarEntity calendarEntity;
    public CanCalendarEntityFitInSlots(){
    }
    public static boolean canFit(ArrayList<TimeSlot> availableSlots, CalendarEntity calendarEntity){
        boolean canFit = false;
        for (TimeSlot slot : availableSlots) {
            if (calendarEntity.getStartTime().isAfter(slot.getStartTime()) && calendarEntity.getEndTime().isBefore(slot.getEndTime())) {
                canFit = true;
                break;
            }
        }
        return canFit;
    }

}
