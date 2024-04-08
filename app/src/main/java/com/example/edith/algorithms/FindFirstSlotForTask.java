package com.example.edith.algorithms;

import android.util.Log;

import com.example.edith.controllers.SchedulerController;
import com.example.edith.models.CalendarEntity;
import com.example.edith.models.Task;
import com.example.edith.models.TimeSlot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;


public class FindFirstSlotForTask {
    public FindFirstSlotForTask() {
    }
    public static ArrayList<CalendarEntity> find(ArrayList<TimeSlot> availableSlots, long duration, String deadline, String taskName, ArrayList<CalendarEntity> calendarEntities) throws ParseException {
        Instant now = Instant.now();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (availableSlots == null) {
            Log.d("FindFirstSlotForTask","no available slots");
            return null;
        }
        ArrayList<CalendarEntity> rescheduledCalendarEntities = new ArrayList<>();
        if (!availableSlots.isEmpty()) {
            for (int i = 0; i < availableSlots.size(); i++) {
                TimeSlot availableSlot = availableSlots.get(i);
                if (availableSlot.getDuration() <= duration) {
                    CalendarEntity newCalendarEntity = new CalendarEntity(taskName, availableSlot.getStartTime(), availableSlot.getEndTime());
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
                    if (nonTaskEntity.getTimeSlot().startTimeIsAfterTime(df.format(now)) && nonTaskEntity.getTimeSlot().startTimeIsBeforeTime(df.format(deadline))) {
                        String proposedEndTime = null;
                        try {
                            proposedEndTime = df.format(df.parse(nonTaskEntity.getStartTime()).getTime()+ holdEntity.getDurationMinutes());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            if (df.parse(proposedEndTime).getTime() < df.parse(nonTaskEntity.getEndTime()).getTime())
                            nonTaskEntity.getEndTime();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        // The holdEntity can be placed in the tempEntity slot
                            holdEntity.setEndTime(proposedEndTime);
                            calendarEntities.add(holdEntity);
                            // Update the start time of the tempEntity to reflect the placement of the holdEntity
                            nonTaskEntity.setStartTime(proposedEndTime);
                            isPlaced = true;
                            break;
                        }
                    }

                if (!isPlaced) {
                    for (int i = 0; i < holdEntities.size(); i++) {
                        if (holdEntities.get(i).getDurationMinutes() < duration) {
                            break;
                        }
                        if (holdEntities.get(i).isReschedulable()) {
                            CalendarEntity newCalendarEntity = new CalendarEntity(taskName, holdEntities.get(i).getStartTime(), df.format(df.parse(holdEntities.get(i).getStartTime()).getTime() + holdEntities.get(i).getDurationMinutes()));
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
