package com.example.edith.algorithms;

import android.util.Log;

import com.example.edith.controllers.SchedulerController;
import com.example.edith.models.CalendarEntity;
import com.example.edith.models.Task;
import com.example.edith.models.TimeSlot;

import java.time.ZonedDateTime;
import java.util.ArrayList;


public class FindFirstSlotForTask {
    public FindFirstSlotForTask() {
    }
    public static ArrayList<CalendarEntity> find(ArrayList<TimeSlot> availableSlots, int duration, ZonedDateTime deadline, String taskName, ArrayList<CalendarEntity> calendarEntities) {
        ZonedDateTime now = ZonedDateTime.now();
        if (availableSlots == null) {
            Log.d("FindFirstSlotForTask","no available slots");
            return null;
        }
        ArrayList<CalendarEntity> rescheduledCalendarEntities = new ArrayList<>();
        if (!availableSlots.isEmpty()) {
            for (int i = 0; i < availableSlots.size(); i++) {
                TimeSlot availableSlot = availableSlots.get(i);
                if (availableSlot.getDuration() <= duration) {
                    CalendarEntity newCalendarEntity = new CalendarEntity(taskName, availableSlot.getStartTime(), availableSlot.getStartTime().plusMinutes(availableSlot.getDuration()));
                    rescheduledCalendarEntities.add(newCalendarEntity);
                    return rescheduledCalendarEntities;
                }
            }
            //If no available slot exists
            //Attempt to rearrange the existing tasks to make space for the new task
            ArrayList<CalendarEntity> holdEntities = new ArrayList<>();
            for (CalendarEntity entity : calendarEntities) {
                if (entity.getType().equals("task")) {
                    holdEntities.add(entity);
                    calendarEntities.remove(entity);
                }
            }

            ArrayList<TimeSlot> availableSlots2 = FindAvailableSlots.getAvailableSlots(calendarEntities, duration, deadline);
            holdEntities.sort((e1, e2) -> Long.compare(e2.getDurationMinutes(), e1.getDurationMinutes()));
            for (CalendarEntity holdEntity : holdEntities) {
                boolean isPlaced = false;
                for (CalendarEntity nonTaskEntity : calendarEntities) {
                    if (nonTaskEntity.getStartTime().isAfter(now) && nonTaskEntity.getEndTime().isBefore(deadline)) {
                        ZonedDateTime proposedEndTime = nonTaskEntity.getStartTime().plusMinutes(holdEntity.getDurationMinutes());
                        if (proposedEndTime.isBefore(nonTaskEntity.getEndTime())) {
                            // The holdEntity can be placed in the tempEntity slot
                            holdEntity.setEndTime(proposedEndTime);
                            calendarEntities.add(holdEntity);
                            // Update the start time of the tempEntity to reflect the placement of the holdEntity
                            nonTaskEntity.setStartTime(proposedEndTime);
                            isPlaced = true;
                            break;
                        }
                    }
                }
                if (!isPlaced) {
                    for (int i = 0; i < holdEntities.size(); i++) {

                        if (holdEntities.get(i).getDurationMinutes() < duration) {
                            break;
                        }
                        if (holdEntities.get(i).isReschedulable()) {
                            CalendarEntity newCalendarEntity = new CalendarEntity(taskName, holdEntities.get(i).getStartTime(), holdEntities.get(i).getStartTime().plusMinutes(Integer.parseInt(String.valueOf(holdEntities.get(i).getDurationMinutes()))));
                            rescheduledCalendarEntities.add(newCalendarEntity);
                            rescheduledCalendarEntities.add(SchedulerController.rescheduleTaskRequest((Task) holdEntities.get(i)));
                            break;
                        }
                    }
                    return null;
                }
            }
            //Compare finalEntities with calendarEntities
            for (CalendarEntity entity : calendarEntities) {
                if (!calendarEntities.contains(entity)) {
                    rescheduledCalendarEntities.add(entity);
                }
                if (calendarEntities.contains(entity)) {
                    for (CalendarEntity entity1 : calendarEntities) {
                        if (entity1.equals(entity)) {
                            //If entity in finalEntities has different start time in calendarEntities
                            if (!entity1.getStartTime().equals(entity.getStartTime())) {
                                //Add entity to rescheduledCalendarEntities
                                rescheduledCalendarEntities.add(entity);
                            }
                        }
                    }
                }
            }
        }

        return rescheduledCalendarEntities;
    }


}
