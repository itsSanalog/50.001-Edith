package com.example.edith.data;

import com.example.edith.models.CalendarEntity;
import com.example.edith.models.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public interface DatabaseOperations {
    Task getTask(String id);

    List<Task> getAllTasks();
    Task getTask(int position);

    void addTask(Task task);

    void removeTask(int position);
    void removeTask(String id);

    void updateTask(Task task);

    void updateTaskStatus(String id, boolean status);

    ArrayList<CalendarEntity> getAllCalendarEntities();

    int getSize();

    void repopulateTaskList(QuerySnapshot snapshot);
    void repopulateEventList(QuerySnapshot snapshot);

}