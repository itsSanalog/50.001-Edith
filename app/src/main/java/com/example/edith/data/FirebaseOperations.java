package com.example.edith.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.edith.adapters.TaskAdapter;
import com.example.edith.models.CalendarEntities.CalendarEntity;
import com.example.edith.models.CalendarEntities.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.edith.models.CalendarEntities.Task;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class handles operations related to Firebase.
 * It implements the DatabaseOperations interface.
 */
public class FirebaseOperations implements DatabaseOperations {
    // Create final string for Logcat
    private static final String TAG = "FirebaseOperations";

    private static FirebaseOperations instance = null;
    private FirebaseFirestore firestore;
    private CollectionReference taskDatabaseReference;
    private CollectionReference eventDatabaseReference;

    List<Task> taskList;
    List<Event> eventList;
    private TaskAdapter adapter;
    int size;

    /**
     * Private constructor for Singleton Design Pattern.
     * Initializes the database and sets up listeners for tasks and events.
     */
    private FirebaseOperations(){
        // Initialize the database
        firestore = FirebaseFirestore.getInstance();
        taskDatabaseReference = firestore.collection("tasks");
        eventDatabaseReference = firestore.collection("events");

        // Get all tasks from the database
        taskDatabaseReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                countListItems(value);
                repopulateTaskList(value);
                Log.d(TAG, taskList.toString());
                if (adapter != null){
                    adapter.notifyDataSetChanged();
                }
                GoogleCalendarOperations.getInstance().syncCalendarEntities();
            }
        });

        eventDatabaseReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                countListItems(value);
                repopulateEventList(value);
                GoogleCalendarOperations.getInstance().syncCalendarEntities();
            }
        });
    }

    /**
     * Creates a singleton instance of the class.
     * @return the singleton instance.
     */
    public static FirebaseOperations getInstance(){
        if (instance == null){
            instance = new FirebaseOperations();
        }
        return instance;
    }

    /**
     * Sets the adapter for tasks.
     * @param adapter the TaskAdapter to set.
     */
    public void setAdapter(TaskAdapter adapter){
        this.adapter = adapter;
    }

    /**
     * Counts the items in a QuerySnapshot.
     * @param snapshots the QuerySnapshot to count items in.
     */
    private void countListItems(QuerySnapshot snapshots){
        size = snapshots.size();
    }

    /**
     * Repopulates the task list from a QuerySnapshot.
     * @param snapshots the QuerySnapshot to repopulate from.
     */
    @Override
    public void repopulateTaskList(QuerySnapshot snapshots){
        taskList = new ArrayList<>();

        if (snapshots != null){
            taskList.addAll(snapshots.toObjects(Task.class));
        }
    }

    /**
     * Repopulates the event list from a QuerySnapshot.
     * @param snapshots the QuerySnapshot to repopulate from.
     */
    @Override
    public void repopulateEventList(QuerySnapshot snapshots){
        eventList = new ArrayList<>();

        if (snapshots != null){
            eventList.addAll(snapshots.toObjects(Event.class));
        }
    }

    /**
     * Gets a task by its position in the list.
     * @param position the position of the task in the list.
     * @return the task at the given position.
     */
    public Task getTask(int position){
        return taskList.get(position);
    }

    /**
     * Gets a task by its ID.
     * @param id the ID of the task.
     * @return the task with the given ID.
     */
    public Task getTask(String id){
        // Get task from the database
        Task task =  taskDatabaseReference.document(id).get().getResult().toObject(Task.class);
        return task;
    }

    /**
     * Gets all tasks.
     * @return a list of all tasks.
     */
    public List<Task> getAllTasks(){
        return taskList;
    }

    /**
     * Adds a task to the database.
     * @param task the task to add.
     */
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
        taskMap.put("updateRequired", task.getUpdateRequired());
        taskMap.put("type", task.getType());


        taskDatabaseReference.document(task.getEntityID()).set(taskMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
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

    /**
     * Removes a task from the database by its position in the list.
     * @param position the position of the task in the list.
     */
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

    /**
     * Removes a task from the database by its ID.
     * @param id the ID of the task.
     */
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

    /**
     * Updates a task in the database.
     * @param task the task with updated information.
     */
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
        taskMap.put("updateRequired", task.getUpdateRequired());
        taskMap.put("type", task.getType());


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

    /**
     * Gets all calendar entities.
     * @return a list of all calendar entities.
     */
    public List<CalendarEntity> getAllCalendarEntities(){
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

    /**
     * Updates the status of a task in the database.
     * @param id the ID of the task.
     * @param status the new status of the task.
     */
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

    /**
     * Gets the size of the task list.
     * @return the size of the task list.
     */
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
            }
        });
        return size;
    }
}