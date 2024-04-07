package com.example.edith.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

// This class is used to get the ID of the task from the Firestore database.
public class TaskID {
    // Exclude annotation is used to exclude the TaskID from the Firestore database.
    // Exclude TaskID from all manipulations in the Firestore database.
    @Exclude
    public String TaskID;

    public <T extends TaskID> T withId(@NonNull final String id) {
        this.TaskID = id;
        return (T) this;
    }
}
