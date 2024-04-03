package com.example.edith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.edith.algorithms.FindAvailableSlots;
import com.example.edith.models.CalendarEntity;
import com.example.edith.models.TimeSlot;

import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class FindAvailableSlotsTest {

    @Test
    public void getAvailableSlotsWhenNoExistingEntities() {
        ArrayList<CalendarEntity> existingEntities = new ArrayList<>();
        int duration = 30;
        ZonedDateTime deadline = ZonedDateTime.now().plusDays(1);

        ArrayList<TimeSlot> result = FindAvailableSlots.getAvailableSlots(existingEntities, duration, deadline);
        assertFalse(result.isEmpty());
    }

    @Test
    public void getAvailableSlotsWhenExistingEntitiesOverlap() {
        ArrayList<CalendarEntity> existingEntities = new ArrayList<>();
        existingEntities.add(new CalendarEntity("Existing Task", ZonedDateTime.now().plusMinutes(10), ZonedDateTime.now().plusMinutes(20)));
        int duration = 30;
        ZonedDateTime deadline = ZonedDateTime.now().plusDays(1);

        ArrayList<TimeSlot> result = FindAvailableSlots.getAvailableSlots(existingEntities, duration, deadline);
        assertTrue(result.isEmpty());
    }

    @Test
    public void isSlotAvailableWhenNoOverlap() {
        TimeSlot potentialSlot = new TimeSlot(ZonedDateTime.now().plusMinutes(30), 30);

        assertTrue(FindAvailableSlots.isSlotAvailable(potentialSlot));
    }

    @Test
    public void isSlotAvailableWhenOverlap() {
        TimeSlot potentialSlot = new TimeSlot(ZonedDateTime.now().plusMinutes(10), 30);

        assertFalse(FindAvailableSlots.isSlotAvailable(potentialSlot));
    }
}