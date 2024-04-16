package com.example.edith.firestore;

import android.util.Log;

import com.example.edith.activities.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentChange;

import org.testng.reporters.jq.Main;

import java.util.ArrayList;

public class FirestoreObservable implements DatabaseObservable {
    private FirebaseFirestore db;

    private ArrayList<CalendarObserver> observers = new ArrayList<>();


    public FirestoreObservable() {
//        db = FirebaseFirestore.getInstance();
//        db.collection("tasks").addSnapshotListener((QuerySnapshot snapshots,
//                                                    FirebaseFirestoreException e) -> {
//            if (e != null) {
//                // Handle error
//                return;
//            }
//
//            for (DocumentChange dc : snapshots.getDocumentChanges()) {
//                Log.d("firestoreObservable", "Document change type: " + dc.getType());
//                switch (dc.getType()) {
//                    case ADDED:
//                    case MODIFIED:
//                    case REMOVED:
//                        notifyObservers();
//                        break;
//                }
//            }
//        });
    }

    @Override
    public void addObserver(CalendarObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(CalendarObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (CalendarObserver observer : observers) {
            Log.d("firestoreObservable", "Notifying observers" + observer.toString());
        }
    }
}