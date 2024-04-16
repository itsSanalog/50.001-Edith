package com.example.edith.firestore;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentChange;

import java.util.ArrayList;

public class firestoreObservable implements databaseObservable{
    private FirebaseFirestore db;

    private ArrayList<databaseObserver> observers = new ArrayList<>();

    public firestoreObservable() {
        db = FirebaseFirestore.getInstance();
        db.collection("tasks").addSnapshotListener((QuerySnapshot snapshots,
                                                    FirebaseFirestoreException e) -> {
            if (e != null) {
                // Handle error
                return;
            }

            for (DocumentChange dc : snapshots.getDocumentChanges()) {
                switch (dc.getType()) {
                    case ADDED:
                    case MODIFIED:
                    case REMOVED:
                        notifyObservers();
                        break;
                }
            }
        });
    }

    @Override
    public void addObserver(databaseObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(databaseObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (databaseObserver observer : observers) {
            observer.update();
        }
    }
}