package com.example.edith.firestore;

public interface DatabaseObservable {
    void addObserver(CalendarObserver observer);
    void removeObserver(CalendarObserver observer);
    void notifyObservers();
}
