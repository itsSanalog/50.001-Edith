package com.example.edith.data;

import com.example.edith.models.CalendarEntity;
import com.example.edith.models.Task;

import java.util.ArrayList;
import java.util.List;

public interface DatabaseOperations {
    Task getTask(String id);

    List<Task> getAllTasks();

    void addTask(Task task);

    void removeTask(String id);

    void updateTask(Task task);

    void updateTaskStatus(String id, boolean status);

    ArrayList<CalendarEntity> getAllCalendarEntities();
}