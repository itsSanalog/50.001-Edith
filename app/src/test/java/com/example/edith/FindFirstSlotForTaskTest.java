package com.example.edith;

import static org.junit.jupiter.api.Assertions.*;

import com.example.edith.algorithms.FindFirstSlotForTask;
import com.example.edith.models.CalendarEntity;
import com.example.edith.models.TimeSlot;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class FindFirstSlotForTaskTest {

    @Test
    public void findSlotWhenAvailableSlotsAreNull() {
        ArrayList<TimeSlot> availableSlots = null;
        int duration = 30;
        ZonedDateTime deadline = ZonedDateTime.now().plusDays(1);
        String taskName = "Test Task";
        ArrayList<CalendarEntity> calendarEntities = new ArrayList<>();

        assertNull(FindFirstSlotForTask.find(availableSlots, duration, deadline, taskName, calendarEntities));
    }

    @Test
    public void findSlotWhenAvailableSlotsAreEmpty() {
        ArrayList<TimeSlot> availableSlots = new ArrayList<>();
        int duration = 30;
        ZonedDateTime deadline = ZonedDateTime.now().plusDays(1);
        String taskName = "Test Task";
        ArrayList<CalendarEntity> calendarEntities = new ArrayList<>();

        ArrayList<CalendarEntity> result = FindFirstSlotForTask.find(availableSlots, duration, deadline, taskName, calendarEntities);
        assertTrue(result.isEmpty());
    }

    @Test
    public void findSlotWhenAvailableSlotsAreNotEmpty() {
        ArrayList<TimeSlot> availableSlots = new ArrayList<>();
        availableSlots.add(new TimeSlot(ZonedDateTime.now(),60));
        int duration = 30;
        ZonedDateTime deadline = ZonedDateTime.now().plusDays(1);
        String taskName = "Test Task";
        ArrayList<CalendarEntity> calendarEntities = new ArrayList<>();

        ArrayList<CalendarEntity> result = FindFirstSlotForTask.find(availableSlots, duration, deadline, taskName, calendarEntities);
        assertFalse(result.isEmpty());
    }

    @Test
    public void findSlotWhenTaskCannotFitInAvailableSlots() {
        ArrayList<TimeSlot> availableSlots = new ArrayList<>();
        availableSlots.add(new TimeSlot(ZonedDateTime.now(),10));
        int duration = 30;
        ZonedDateTime deadline = ZonedDateTime.now().plusDays(1);
        String taskName = "Test Task";
        ArrayList<CalendarEntity> calendarEntities = new ArrayList<>();

        ArrayList<CalendarEntity> result = FindFirstSlotForTask.find(availableSlots, duration, deadline, taskName, calendarEntities);
        assertTrue(result.isEmpty());
    }
}