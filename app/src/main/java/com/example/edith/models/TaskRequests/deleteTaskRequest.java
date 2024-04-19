package com.example.edith.models.TaskRequests;

/**
 * deleteTaskRequest is a class that extends TaskRequest and represents a request to delete a task.
 * It provides a constructor to initialize the task id and a method to get the task id.
 */
public class deleteTaskRequest extends TaskRequest {
    private final String id;

    /**
     * Constructor for deleteTaskRequest.
     * Initializes the task id with the provided value.
     * @param id The id of the task to be deleted.
     */
    public deleteTaskRequest(String id){
        super(id, null, null, 0);
        this.id = id;
    }

    /**
     * Returns the id of the task to be deleted.
     * @return The id of the task to be deleted.
     */
    public String getId() {
        return id;
    }
}