package com.example.edith.models;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseOperations {

        public static void createEntity(CalendarEntity entity) {
            // Code to create entity in Firebase
            DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("CalendarEntities").child(entity.getEntityID()).setValue(entity);
        }

        public static void update(CalendarEntity entity) {
            // Code to update entity in Firebase

        }

        public static void delete(String entityID) {
            // Code to delete entity in Firebase
        }

        public static ArrayList<CalendarEntity> readEntities() {
            // Code to read entities from Firebase
            DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference("CalendarEntities");
            final ArrayList<CalendarEntity>[] entities = new ArrayList[]{null};
            ValueEventListener valueEventListener = mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        CalendarEntity entity = postSnapshot.getValue(CalendarEntity.class);
                    }
                    entities[0] = dataSnapshot.getValue(ArrayList.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "Failed to read data from firebase", databaseError.toException());
                }
            });
            return entities[0];
        }
}
