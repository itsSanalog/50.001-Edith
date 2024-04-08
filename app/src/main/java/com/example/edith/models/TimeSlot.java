package com.example.edith.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSlot {
    private long startTime;
    private long endTime;
    private int duration; //In minutes

    public TimeSlot() {
    }

    public TimeSlot(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = df.parse(startTime);
            this.startTime = date.getTime();
            date = df.parse(endTime);
            this.endTime = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TimeSlot(String startTime, long duration) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = df.parse(startTime);
            this.startTime = date.toInstant().getEpochSecond();
            this.endTime = date.toInstant().getEpochSecond() + duration *60;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStartTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(startTime);
    }

    public String getEndTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(endTime);
    }

    public long getDuration() {
        return (endTime - startTime) /60;
    }

    public void setStartTime(String startTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = df.parse(startTime);
            this.startTime = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setEndTime(String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = df.parse(endTime);
            this.endTime = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean startTimeIsAfterTimeslot(TimeSlot slot) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(this.getStartTime()).after(df.parse(slot.getStartTime() ));

        } catch (NullPointerException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean startTimeIsBeforeTimeslot(TimeSlot slot) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(this.getStartTime()).before(df.parse(slot.getStartTime() ));

        } catch (NullPointerException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean startTimeIsAfterTime(String startTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(this.getStartTime()).after(df.parse(startTime));
        } catch (NullPointerException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean startTimeIsBeforeTime(String startTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(this.getStartTime()).before(df.parse(startTime));
        } catch (NullPointerException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}