package com.example.edith.data;

import android.content.Context;

import com.example.edith.models.CalendarEntity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleCalendarListener {
    private GoogleSignInAccount account;
    private static GoogleCalendarListener instance;
    private GoogleAccountCredential credential;
    private GoogleCalendarListener() {
    }
    public static GoogleCalendarListener getInstance() {
        if (instance == null){
            instance = new GoogleCalendarListener();
        }
        return instance;
    }
    public void setAccount(GoogleSignInAccount account, Context context) {
        this.account = account;
        this.credential = GoogleAccountCredential.usingOAuth2(
                        context, Collections.singleton("https://www.googleapis.com/auth/calendar"))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(account.getEmail());
    }
    public List<CalendarEntity> getAllCalendarEntities() {
        List<CalendarEntity> calendarEntities = new ArrayList<>();
        calendarEntities.add(new CalendarEntity("Important meeting", "2024-04-17T13:00:00", "2024-04-17T17:00:00", "Test"));
        calendarEntities.add(new CalendarEntity("Another important meeting", "2024-04-17T21:30:00", "2024-04-17T23:30:00", "Test"));
        return calendarEntities;
    }

//    public List<CalendarEntity> getAllCalendarEntities() {
//        Calendar service = new Calendar.Builder(
//                AndroidHttp.newCompatibleTransport(),
//                JacksonFactory.getDefaultInstance(),
//                credential)
//                .setApplicationName("Edith")
//                .build();
//
//        // List the next 10 events from the primary calendar.
//        Events events = null;
//        try {
//            events = service.events().list("primary")
//                    .setMaxResults(10)
//                    .setOrderBy("startTime")
//                    .setSingleEvents(true)
//                    .execute();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        List<Event> items = events.getItems();
//
//        List<CalendarEntity> calendarEntities = new ArrayList<>();
//        for (Event event : items) {
//            CalendarEntity calendarEntity = new CalendarEntity(event.getSummary(), event.getStart().toString(), event.getEnd().toString(), event.getDescription(),event.getId());
//
//            calendarEntities.add(calendarEntity);
//        }
//
//        return calendarEntities;
//    }
}