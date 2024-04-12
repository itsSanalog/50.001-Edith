package com.example.edith.data;

import android.util.Log;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.edith.models.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Result;

public class FirebaseOperations implements DatabaseOperations {
    // Create final string for Logcat
    private static final String TAG = "FirebaseOperations";

    private static FirebaseOperations instance = null;
    private FirebaseFirestore firestore;
    private CollectionReference taskDatabaseReference;

    // Create a private constructor: Singleton Design Pattern
    private FirebaseOperations(){
        // Initialize the database
        firestore = FirebaseFirestore.getInstance();
        taskDatabaseReference = firestore.collection("tasks");
    }

    // Create a singleton instance of the class
    public static FirebaseOperations getInstance(){
        if (instance == null){
            instance = new FirebaseOperations();
        }
        return instance;
    }

    public void addTask(Task task){
        Map<String, Object> taskMap = new HashMap<>();
        // Add task to the database
        taskMap.put("id", task.getEntityID());
        taskMap.put("title", task.getEntityTitle());
        taskMap.put("description", task.getDescription());
        taskMap.put("status", task.isCompleted());
        taskMap.put("priority", task.getPriority());
        taskMap.put("deadline", task.getDeadline());
        taskMap.put("duration", task.getDurationMinutes());
        taskMap.put("start_time", task.getStartTime());
        taskMap.put("end_time", task.getEndTime());
        taskMap.put("TimeSlot", task.getTimeSlot());

        taskDatabaseReference.add(taskMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentReference> task) {
                // Log success message
                Log.d(TAG, "Task added successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Task failed to add");
            }
        });
    }

    public void removeTask(String id){
        // Remove task from the database
        taskDatabaseReference.document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                // Log success message
                Log.d(TAG, "Task removed successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Task failed to remove");
            }
        });
    }

    public void updateTask(Task task){
        Map<String, Object> taskMap = new HashMap<>();
        // Update task in the database
        taskMap.put("id", task.getEntityID());
        taskMap.put("title", task.getEntityTitle());
        taskMap.put("description", task.getDescription());
        taskMap.put("status", task.isCompleted());
        taskMap.put("priority", task.getPriority());
        taskMap.put("deadline", task.getDeadline());
        taskMap.put("duration", task.getDurationMinutes());
        taskMap.put("start_time", task.getStartTime());
        taskMap.put("end_time", task.getEndTime());
        taskMap.put("TimeSlot", task.getTimeSlot());

        taskDatabaseReference.document(task.getEntityID()).set(taskMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                // Log success message
                Log.d(TAG, "Task updated successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Task failed to update");
            }
        });
    }

    public Task getTask(String id){
        // Get task from the database
        Task task =  taskDatabaseReference.document(id).get().getResult().toObject(Task.class);
        return task;
    }

    public List<Task> getAllTasks(){
        // Get all tasks from the database
        final List<Task> tasks = new ArrayList<>();
        taskDatabaseReference
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (Task task : queryDocumentSnapshots.toObjects(Task.class)){
                    tasks.add(task);
                }
            }

        });
        return tasks;
    }

    @Override
    public void updateTaskStatus(String id, boolean status) {
        // Update task status in the database
        taskDatabaseReference.document(id).update("status", status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                // Log success message
                Log.d(TAG, "Task status updated successfully to " + status);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Task status failed to update to " + status);
            }
        });
    }
}
