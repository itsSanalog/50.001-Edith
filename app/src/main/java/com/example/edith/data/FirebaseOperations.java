package com.example.edith.data;

import android.util.Log;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.edith.adapters.TaskAdapter;
import com.example.edith.models.CalendarEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.edith.models.Task;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
    List<Task> taskList;
    private TaskAdapter adapter;
    int size;

    // Create a private constructor: Singleton Design Pattern
    public FirebaseOperations(){
        // Initialize the database
        firestore = FirebaseFirestore.getInstance();
        taskDatabaseReference = firestore.collection("tasks");

        // Get all tasks from the database
        taskDatabaseReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Log.d(TAG, "Error getting tasks: " + error);
                }
                Log.d(TAG, "Tasks: " + value);
                countListItems(value);
                repopulateList(value);
                Log.d(TAG, taskList.toString());
                if (adapter != null){
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    // Create a singleton instance of the class
    public static FirebaseOperations getInstance(){
        if (instance == null){
            instance = new FirebaseOperations();
        }
        return instance;
    }

    public void setAdapter(TaskAdapter adapter){
        this.adapter = adapter;
    }

    // Count List
    private void countListItems(QuerySnapshot snapshots){
        size = snapshots.size();
        Log.d(TAG, "Size: " + size);
    }

    // Repopulate list
    @Override
    public void repopulateList(QuerySnapshot snapshots){
        taskList = new ArrayList<>();

        if (snapshots != null){
            taskList.addAll(snapshots.toObjects(Task.class));
            Log.d("TaskAdapterDB", "Size: " + taskList.size());
        }
    }

    // GetTask
    public Task getTask(int position){
        Log.i("TaskAdapterDB1", "test" + taskList.get(position).getEntityTitle());
        Log.i("TaskAdapterDB2", "test" + taskList.get(position));

        return taskList.get(position);
    }

    public Task getTask(String id){
        // Get task from the database
        Task task =  taskDatabaseReference.document(id).get().getResult().toObject(Task.class);
        return task;
    }

    public List<Task> getAllTasks(){
        return taskList;
    }

    // AddTask
    public void addTask(Task task){
        Map<String, Object> taskMap = new HashMap<>();
        // Add task to the database
        taskMap.put("entityID", task.getEntityID());
        taskMap.put("entityTitle", task.getEntityTitle());
        taskMap.put("description", task.getDescription());
        taskMap.put("isCompleted", task.isCompleted());
        taskMap.put("priority", task.getPriority());
        taskMap.put("deadline", task.getDeadline());
        taskMap.put("durationMinutes", task.getDurationMinutes());
        taskMap.put("start_time", task.getStartTime());
        taskMap.put("end_time", task.getEndTime());
        taskMap.put("timeSlot", task.getTimeSlot());

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

    // RemoveTask
    @Override
    public void removeTask(int position) {
        Task task = taskList.get(position);

        // Remove task from the database
        taskDatabaseReference.document(task.getEntityID()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
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
        taskMap.put("entityID", task.getEntityID());
        taskMap.put("entityTitle", task.getEntityTitle());
        taskMap.put("description", task.getDescription());
        taskMap.put("isCompleted", task.isCompleted());
        taskMap.put("priority", task.getPriority());
        taskMap.put("deadline", task.getDeadline());
        taskMap.put("durationMinutes", task.getDurationMinutes());
        taskMap.put("start_time", task.getStartTime());
        taskMap.put("end_time", task.getEndTime());
        taskMap.put("timeSlot", task.getTimeSlot());

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

    public ArrayList<CalendarEntity> getAllCalendarEntities(){
        // Get all entities from the database
        final ArrayList<CalendarEntity> calendarEntities = new ArrayList<>();
        taskDatabaseReference
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        calendarEntities.addAll(queryDocumentSnapshots.toObjects(Task.class));
                    }

                });
        return calendarEntities;
    }

    public void addCalendarEntity(CalendarEntity calendarEntity){
        Map<String, Object> calendarEntityMap = new HashMap<>();
        // Add task to the database
        calendarEntityMap.put("id", calendarEntity.getEntityID());
        calendarEntityMap.put("title", calendarEntity.getEntityTitle());
        calendarEntityMap.put("description", calendarEntity.getDescription());
        calendarEntityMap.put("priority", calendarEntity.getPriority());
        calendarEntityMap.put("duration", calendarEntity.getDurationMinutes());
        calendarEntityMap.put("start_time", calendarEntity.getStartTime());
        calendarEntityMap.put("end_time", calendarEntity.getEndTime());
        calendarEntityMap.put("TimeSlot", calendarEntity.getTimeSlot());

        taskDatabaseReference.add(calendarEntityMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentReference> task) {
                // Log success message
                Log.d(TAG, "Calendar Entity added successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Calendar Entity failed to add");
            }
        });
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

    public int getSize(){
        taskDatabaseReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (taskList == null) {
                    taskList = new ArrayList<>();
                    taskList.addAll(queryDocumentSnapshots.toObjects(Task.class));
                    Log.d(TAG, "If size taskList == null getSize is :  " + taskList.size());
                }
                size = taskList.size();
                Log.d(TAG, "If size taskList is not null getSize is : " + size);


//        if (taskList == null){
//            repopulateList(taskDatabaseReference.get().getResult());
//            Log.d(TAG, "If size taskList == null getSize is :  " + size);
//        }
//        Log.d(TAG, "If size taskList is not null getSize is : " + size);
//        return size;
            }
        });
        return size;
    }
}
