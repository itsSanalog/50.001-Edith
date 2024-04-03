package com.example.edith.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LocalEntityStorage extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "edith.db";
    private static final int DATABASE_VERSION = 1;

    public LocalEntityStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE CalendarEntity (" +
                "entityID TEXT PRIMARY KEY," +
                "createdDateTime TEXT," +
                "entityTitle TEXT," +
                "durationMinutes INTEGER," +
                "start_time TEXT," +
                "end_time TEXT," +
                "description TEXT," +
                "priority INTEGER," +
                "isScheduled INTEGER," +
                "isCompleted INTEGER," +
                "isOverdue INTEGER," +
                "deadline TEXT," +
                "type TEXT" +
                ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CalendarEntity");
        onCreate(db);
    }

    public void create(CalendarEntity entity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("entityID", entity.getEntityID());
        values.put("createdDateTime", entity.getCreatedDateTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        values.put("entityTitle", entity.getEntityTitle());
        values.put("durationMinutes", entity.getDurationMinutes());
        values.put("start_time", entity.getStartTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        values.put("end_time", entity.getEndTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        values.put("description", entity.getDescription());
        values.put("priority", entity.getPriority());
        values.put("isScheduled", entity.isScheduled() ? 1 : 0);
        if (entity instanceof Task) {
            Task task = (Task) entity;
            values.put("isCompleted", task.isCompleted() ? 1 : 0);
            values.put("isOverdue", task.isOverdue() ? 1 : 0);
            values.put("deadline", task.getDeadline().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        }
        values.put("type", entity.getType());

        db.insert("CalendarEntity", null, values);
        db.close();
    }

    public ArrayList<CalendarEntity> read() {
        ArrayList<CalendarEntity> entities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("CalendarEntity", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String entityID = cursor.getString(0);
                ZonedDateTime createdDateTime = ZonedDateTime.parse(cursor.getString(1), DateTimeFormatter.ISO_ZONED_DATE_TIME);
                String entityName = cursor.getString(2);
                int durationMinutes = cursor.getInt(3);
                ZonedDateTime startTime = ZonedDateTime.parse(cursor.getString(4), DateTimeFormatter.ISO_ZONED_DATE_TIME);
                ZonedDateTime endTime = ZonedDateTime.parse(cursor.getString(5), DateTimeFormatter.ISO_ZONED_DATE_TIME);
                String description = cursor.getString(6);
                int priority = cursor.getInt(7);
                boolean isScheduled = cursor.getInt(8) == 1;
                String type = cursor.getString(12);

                CalendarEntity entity;
                if (type.equals("Task")) {
                    boolean isCompleted = cursor.getInt(9) == 1;
                    boolean isOverdue = cursor.getInt(10) == 1;
                    ZonedDateTime deadline = ZonedDateTime.parse(cursor.getString(11), DateTimeFormatter.ISO_ZONED_DATE_TIME);
                    entity = new Task(entityName, startTime, endTime);
                    ((Task) entity).setCompleted(isCompleted);
                    ((Task) entity).setOverdue(isOverdue);
                    ((Task) entity).setDeadline(deadline);
                } else {
                    entity = new CalendarEntity(entityName, startTime, endTime);
                }
                entity.setCreatedDateTime(createdDateTime);
                entity.setDurationMinutes(durationMinutes);
                entity.setDescription(description);
                entity.setPriority(priority);
                entity.setScheduled(isScheduled);

                entities.add(entity);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return entities;
    }

    public void update(CalendarEntity entity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("entityID", entity.getEntityID());
        values.put("createdDateTime", entity.getCreatedDateTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        values.put("entityTitle", entity.getEntityTitle());
        values.put("durationMinutes", entity.getDurationMinutes());
        values.put("start_time", entity.getStartTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        values.put("end_time", entity.getEndTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        values.put("description", entity.getDescription());
        values.put("priority", entity.getPriority());
        values.put("isScheduled", entity.isScheduled() ? 1 : 0);
        if (entity instanceof Task) {
            Task task = (Task) entity;
            values.put("isCompleted", task.isCompleted() ? 1 : 0);
            values.put("isOverdue", task.isOverdue() ? 1 : 0);
            values.put("deadline", task.getDeadline().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        }
        values.put("type", entity.getType());

        db.update("CalendarEntity", values, "entityID = ?", new String[]{String.valueOf(entity.getEntityID())});
        db.close();
    }

    public void delete(String entityID) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int rowsDeleted = db.delete("CalendarEntity", "entityID=?", new String[]{entityID});
            System.out.println("Rows deleted: " + rowsDeleted);
        }
    }
}