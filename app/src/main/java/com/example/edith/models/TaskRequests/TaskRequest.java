package com.example.edith.models.TaskRequests;

/**
 * TaskRequest is a class that represents a task request with an entity name, entity description, task deadline, and duration.
 * It provides methods to get the entity name, entity description, task deadline, and duration.
 * This class acts as a base class for other task request classes.
 */
public class TaskRequest {
    private final String entityName;
    private final String entityDescription;
    private final String taskDeadline;
    private final int duration;

    /**
     * Constructor for TaskRequest.
     * Initializes entityName, entityDescription, taskDeadline, and duration with the provided values.
     * @param entityName The name of the entity.
     * @param entityDescription The description of the entity.
     * @param taskDeadline The deadline of the task.
     * @param duration The duration of the task.
     */
    public TaskRequest(String entityName, String entityDescription, String taskDeadline, int duration) {
        this.entityName = entityName;
        this.entityDescription = entityDescription;
        this.taskDeadline = taskDeadline;
        this.duration = duration;
    }

    /**
     * Returns the deadline of the task.
     * @return The deadline of the task.
     */
    public String getDeadline() {
        return taskDeadline;
    }

    /**
     * Returns the duration of the task.
     * @return The duration of the task.
     */
    public int getDuration() {
        return duration;
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
    public String getDescription() {
        return entityDescription;
    }
}