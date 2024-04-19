package com.example.edith.data;

import android.content.Context;
import android.util.Log;

import com.example.edith.models.CalendarEntities.Task;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * This class handles operations related to Google Calendar.
 * It follows the Singleton Design Pattern.
 */
public class GoogleCalendarOperations {
    private GoogleSignInAccount account;
    private static GoogleCalendarOperations instance;
    private GoogleAccountCredential credential;

    /**
     * Private constructor for Singleton Design Pattern.
     */
    private GoogleCalendarOperations() {
    }

    /**
     * Creates a singleton instance of the class.
     * @return the singleton instance.
     */
    public static GoogleCalendarOperations getInstance() {
        if (instance == null){
            instance = new GoogleCalendarOperations();
        }
        return instance;
    }

    /**
     * Sets the GoogleSignInAccount and creates a GoogleAccountCredential for it.
     * @param account the GoogleSignInAccount to set.
     * @param context the context in which the GoogleAccountCredential is created.
     */
    public void setAccount(GoogleSignInAccount account, Context context) {
        this.account = account;
        this.credential = GoogleAccountCredential.usingOAuth2(
                        context, Collections.singleton("https://www.googleapis.com/auth/calendar"))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(account.getEmail());
    }

    /**
     * Syncs the tasks from Firebase with Google Calendar.
     * This method runs on a new thread.
     */
    public void syncCalendarEntities() {
        new Thread(() -> {
            List<Task> tasks = FirebaseOperations.getInstance().getAllTasks();
            Log.d("GoogleCalendarOperations","test" + tasks.toString());
            Calendar service = new Calendar.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential)
                    .setApplicationName("Edith")
                    .build();

            for (Task task : tasks) {
                if (!task.getUpdateRequired()) {
                    continue;
                }
                Event event = new Event()
                        .setSummary(task.getEntityTitle());
                String encodedTaskID = task.getEntityID().replace("-", "");
                event.setId(encodedTaskID);
                LocalDateTime startDateTime = LocalDateTime.parse(task.getStartTime()).minusHours(8);
                DateTime startDateTimeParsed = DateTime.parseRfc3339(startDateTime.toString());
                EventDateTime start = new EventDateTime()
                        .setDateTime(startDateTimeParsed)
                        .setTimeZone("Asia/Singapore");
                event.setStart(start);

                LocalDateTime endDateTime = LocalDateTime.parse(task.getEndTime()).minusHours(8);
                DateTime endDateTimeParsed = DateTime.parseRfc3339(endDateTime.toString());
                EventDateTime end = new EventDateTime()
                        .setDateTime(endDateTimeParsed)
                        .setTimeZone("Asia/Singapore");
                event.setEnd(end);

                // Insert the new event
                try {
                    event = service.events().insert("primary", event).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                task.setUpdateRequired(false);
                FirebaseOperations.getInstance().addTask(task);

                System.out.printf("Event created: %s\n", event.getHtmlLink());
            }
        }).start();
    }

    /**
     * Deletes a calendar entity from Google Calendar.
     * This method runs on a new thread.
     * @param id the ID of the calendar entity to delete.
     */
    public void deleteCalendarEntity(String id) {
        String encodedID = id.replace("-", "");
        new Thread(() -> {
            Calendar service = new Calendar.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential)
                    .setApplicationName("Edith")
                    .build();
            try {
                service.events().delete("primary", encodedID).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}