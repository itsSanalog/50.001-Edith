package com.example.edith.algorithms;

import com.example.edith.models.CalendarEntity;
import com.example.edith.models.TimeSlot;

import java.util.ArrayList;

public class CanCalendarEntityFitInSlots {
    ArrayList<TimeSlot> availableSlots;
    CalendarEntity calendarEntity;
    public CanCalendarEntityFitInSlots(){
    }
    public static boolean canFit(ArrayList<TimeSlot> availableSlots, CalendarEntity calendarEntity){
        boolean canFit = false;
        for (TimeSlot slot : availableSlots) {
            if (calendarEntity.getTimeSlot().startTimeIsAfterTimeslot(slot) &&
                    calendarEntity.getTimeSlot().startTimeIsBeforeTimeslot(slot)) {
                canFit = true;
                break;
            }
        }
        return canFit;
    }

}