package com.example.edith.data;

import com.example.edith.models.CalendarEntities.CalendarEntity;
import com.example.edith.models.CalendarEntities.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * This interface defines the operations that can be performed on the database.
 */
public interface DatabaseOperations {

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task.
     * @return The task with the given ID.
     */
    Task getTask(String id);

    /**
     * Retrieves a task by its position in the list.
     *
     * @param position The position of the task in the list.
     * @return The task at the given position.
     */
    Task getTask(int position);

    /**
     * Retrieves all tasks.
     *
     * @return A list of all tasks.
     */
    List<Task> getAllTasks();

    /**
     * Adds a task to the database.
     *
     * @param task The task to add.
     */
    void addTask(Task task);

    /**
     * Removes a task from the database by its position in the list.
     *
     * @param position The position of the task in the list.
     */
    void removeTask(int position);

    /**
     * Removes a task from the database by its ID.
     *
     * @param id The ID of the task.
     */
    void removeTask(String id);

    /**
     * Updates a task in the database.
     *
     * @param task The task with updated information.
     */
    void updateTask(Task task);

    /**
     * Updates the status of a task in the database.
     *
     * @param id The ID of the task.
     * @param status The new status of the task.
     */
    void updateTaskStatus(String id, boolean status);

    /**
     * Retrieves all calendar entities.
     *
     * @return A list of all calendar entities.
     */
    List<CalendarEntity> getAllCalendarEntities();

    /**
     * Retrieves the size of the task list.
     *
     * @return The size of the task list.
     */
    int getSize();

    /**
     * Repopulates the task list based on a snapshot of the database.
     *
     * @param snapshot The snapshot of the database.
     */
    void repopulateTaskList(QuerySnapshot snapshot);

    /**
     * Repopulates the event list based on a snapshot of the database.
     *
     * @param snapshot The snapshot of the database.
     */
    void repopulateEventList(QuerySnapshot snapshot);
}