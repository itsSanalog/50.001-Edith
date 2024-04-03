package com.example.edith;

import static org.junit.Assert.assertEquals;

import com.example.edith.models.TimeSlot;

import org.junit.Test;

import java.time.ZonedDateTime;

public class TimeSlotTest {

    @Test
    public void testDuration() {
        ZonedDateTime startTime = ZonedDateTime.now();
        int durationMinutes = 60;
        TimeSlot timeSlot = new TimeSlot(startTime, durationMinutes);

        assertEquals(durationMinutes, timeSlot.getDuration());
    }

    @Test
    public void testStartTime() {
        ZonedDateTime startTime = ZonedDateTime.now();
        int durationMinutes = 60;
        TimeSlot timeSlot = new TimeSlot(startTime, durationMinutes);

        assertEquals(startTime, timeSlot.getStartTime());
    }

    @Test
    public void testEndTime() {
        ZonedDateTime startTime = ZonedDateTime.now();
        int durationMinutes = 60;
        TimeSlot timeSlot = new TimeSlot(startTime, durationMinutes);

        assertEquals(startTime.plusMinutes(durationMinutes), timeSlot.getEndTime());
    }
    @Test
    public void testSetStartTime() {
        ZonedDateTime startTime = ZonedDateTime.now();
        int durationMinutes = 60;
        TimeSlot timeSlot = new TimeSlot(startTime, durationMinutes);

        ZonedDateTime newStartTime = ZonedDateTime.now().plusHours(1);
        timeSlot.setStartTime(newStartTime);

        assertEquals(newStartTime, timeSlot.getStartTime());
    }

    @Test
    public void testSetEndTime() {
        ZonedDateTime startTime = ZonedDateTime.now();
        int durationMinutes = 60;
        TimeSlot timeSlot = new TimeSlot(startTime, durationMinutes);

        ZonedDateTime newEndTime = ZonedDateTime.now().plusHours(2);
        timeSlot.setEndTime(newEndTime);

        assertEquals(newEndTime, timeSlot.getEndTime());
    }

}