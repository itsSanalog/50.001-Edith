package com.example.edith.models;

public class deleteTaskRequest extends TaskRequest {
    private String id;

    public deleteTaskRequest(String id){
        super(id, null, null, 0);
        this.id = id;
    }
}
