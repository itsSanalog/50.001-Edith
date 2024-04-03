package com.example.edith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.edith.algorithms.CanCalendarEntityFitInSlots;
import com.example.edith.models.CalendarEntity;
import com.example.edith.models.TimeSlot;

import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class CanCalendarEntityFitInSlotsTest {

    @Test
    public void canFitWhenSlotIsAvailable() {
        ArrayList<TimeSlot> availableSlots = new ArrayList<>();
        availableSlots.add(new TimeSlot(ZonedDateTime.now().plusMinutes(10),10));
        CalendarEntity calendarEntity = new CalendarEntity("Test Task", ZonedDateTime.now().plusMinutes(15), ZonedDateTime.now().plusMinutes(18));

        assertTrue(CanCalendarEntityFitInSlots.canFit(availableSlots, calendarEntity));
    }

    @Test
    public void cannotFitWhenSlotIsNotAvailable() {
        ArrayList<TimeSlot> availableSlots = new ArrayList<>();
        availableSlots.add(new TimeSlot(ZonedDateTime.now().plusMinutes(10),10));
        CalendarEntity calendarEntity = new CalendarEntity("Test Task", ZonedDateTime.now().plusMinutes(21), ZonedDateTime.now().plusMinutes(25));

        assertFalse(CanCalendarEntityFitInSlots.canFit(availableSlots, calendarEntity));
    }

    @Test
    public void cannotFitWhenAvailableSlotsAreEmpty() {
        ArrayList<TimeSlot> availableSlots = new ArrayList<>();
        CalendarEntity calendarEntity = new CalendarEntity("Test Task", ZonedDateTime.now().plusMinutes(15), ZonedDateTime.now().plusMinutes(18));

        assertFalse(CanCalendarEntityFitInSlots.canFit(availableSlots, calendarEntity));
    }
}