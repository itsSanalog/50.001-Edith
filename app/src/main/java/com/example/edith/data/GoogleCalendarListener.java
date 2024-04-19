package com.example.edith.data;

import android.content.Context;
import com.example.edith.models.CalendarEntities.CalendarEntity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is a singleton that handles operations related to Google Calendar.
 */
public class GoogleCalendarListener {
    private GoogleSignInAccount account;
    private static GoogleCalendarListener instance;
    private GoogleAccountCredential credential;

    /**
     * Private constructor for Singleton Design Pattern.
     */
    private GoogleCalendarListener() {
    }

    /**
     * Creates a singleton instance of the class.
     * @return the singleton instance.
     */
    public static GoogleCalendarListener getInstance() {
        if (instance == null){
            instance = new GoogleCalendarListener();
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
     * Gets all calendar entities.
     * @return a list of all calendar entities.
     */
    public List<CalendarEntity> getAllCalendarEntities() {
        List<CalendarEntity> calendarEntities = new ArrayList<>();
        calendarEntities.add(new CalendarEntity("Important meeting", "2024-04-17T13:00:00", "2024-04-17T17:00:00", "Test"));
        calendarEntities.add(new CalendarEntity("Another important meeting", "2024-04-17T21:30:00", "2024-04-17T23:30:00", "Test"));
        return calendarEntities;
    }
}