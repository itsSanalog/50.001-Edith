package com.example.edith.firestore;

import android.content.Context;
import android.util.Log;

import com.example.edith.activities.GoogleAccountHolder;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.example.edith.data.FirebaseOperations;
import com.example.edith.models.Task;
import com.google.api.client.util.ExponentialBackOff;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

//Google calendar imports
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;


import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;


import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;
import java.io.IOException;


import java.util.Collections;
import java.util.List;

public class GoogleCalendarObserver implements CalendarObserver {
    private Context context;
    private GoogleSignInAccount account;
    public GoogleCalendarObserver(Context context) {

        this.context = context;
        this.firebaseOperations = FirebaseOperations.getInstance();
        this.account = GoogleAccountHolder.getInstance().getAccount();
    }

    private FirebaseOperations firebaseOperations;
    @Override
    public void syncTasks() {
        Log.d("firestoreObserver", "calendarObserver update called");
        List<Task> tasks =  firebaseOperations.getAllTasks();

        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                        context, Collections.singleton("https://www.googleapis.com/auth/calendar"))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(account.getEmail());


        Calendar service = new Calendar.Builder(
                AndroidHttp.newCompatibleTransport(),
                JacksonFactory.getDefaultInstance(),
                credential)
                .setApplicationName("Edith")
                .build();

        for (Task task : tasks) {
            Event event = new Event()
                    .setSummary(task.getEntityTitle());

            DateTime startDateTime = new DateTime(task.getStartTime());
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Asia/Singapore");
            event.setStart(start);

            DateTime endDateTime = new DateTime(task.getEndTime());
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("Asia/Singapore");
            event.setEnd(end);

            // Insert the new event
            try {
                event = service.events().insert("primary", event).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.printf("Event created: %s\n", event.getHtmlLink());
        }
        }












        /*
        FirebaseApp.initializeApp(context); // calendarObserver observer = new calendarObserver(this); when called in main activity
        FirebaseFirestore mRootDatabaseRef = FirebaseFirestore.getInstance().getReference();

        // Write a test entity to Firebase
        CalendarEntity entity = new CalendarEntity("Test", df.format(Instant.now().toEpochMilli()), df.format(Instant.now().toEpochMilli()));
        //FirebaseOperations.createEntity(entity);

        // Read entities from Firebase
        FirebaseOperations.read(new FirebaseOperations.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(ArrayList<CalendarEntity> entities) {
                calendarEntities = entities;
                // Handle the loaded entities here
                Log.d(TAG, "onCreate: " + entities.get(0).getEntityTitle());
                Log.d(TAG, "onCreate: " + calendarEntities.get(0).getEntityTitle());

            }
        });
*/
             // Update an entity in Firebase
             //CalendarEntity entity2 = new CalendarEntity("Test2", df.format(Instant.now().toEpochMilli()), df.format(Instant.now().toEpochMilli()));
             //entity2.setDescription("Updated description");
             //FirebaseOperations.update(entity2);


}






