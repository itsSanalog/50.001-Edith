package com.example.edith.models.TaskRequests;

import android.util.Log;

/**
 * updateTaskRequest is a class that extends TaskRequest and represents a request to update a task.
 * It provides a constructor to initialize the task id, entity name, entity description, task deadline, and duration.
 * It also provides methods to get these values.
 */
public class updateTaskRequest extends TaskRequest {
    private final String id;
    private final String entityName;
    private final String entityDescription;
    private final String taskDeadline;
    private final int duration;

    /**
     * Default Constructor for updateTaskRequest.
     * Initializes the task id, entity name, entity description, task deadline, and duration with the provided values.
     * @param id The id of the task to be updated.
     * @param entityName The name of the entity.
     * @param entityDescription The description of the entity.
     * @param taskDeadline The deadline of the task.
     * @param duration The duration of the task.
     */
    public updateTaskRequest(String id, String entityName, String entityDescription, String taskDeadline, int duration) {
        super(entityName, entityDescription, taskDeadline, duration);
        this.entityName = entityName;
        this.entityDescription = entityDescription;
        this.taskDeadline = taskDeadline;
        this.duration = duration;
        this.id = id;
    }

    /**
     * Returns the id of the task to be updated.
     * @return The id of the task to be updated.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the entity.
     * @return The name of the entity.
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * Returns the description of the entity.
     * @return The description of the entity.
     */
    public String getEntityDescription() {
        return entityDescription;
    }

    /**
     * Returns the deadline of the task.
     * @return The deadline of the task.
     */
    public String getTaskDeadline() {
        Log.d("DeadlineNull", "taskDueDate in updateTaskRequest: " + taskDeadline);

        return taskDeadline;
    }

    /**
     * Returns the duration of the task.
     * @return The duration of the task.
     */
    public int getDuration() {
        return duration;
    }
}