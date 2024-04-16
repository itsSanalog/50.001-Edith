package com.example.edith.firestore;

public interface databaseObservable {
    void addObserver(databaseObserver observer);
    void removeObserver(databaseObserver observer);
    void notifyObservers();
}
