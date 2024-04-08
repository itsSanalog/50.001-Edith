package com.example.edith.models;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class FirebaseOperations {

        public static void createEntity(CalendarEntity entity) {
            // Code to create entity in Firebase
            DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("CalendarEntities").child(entity.getEntityID()).setValue(entity);
        }

        public static void update(CalendarEntity entity) {
            // Code to update entity in Firebase
            DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("CalendarEntities").child(entity.getEntityID()).setValue(entity);
        }

        public static void delete(CalendarEntity entity) {
            // Code to delete entity in Firebase

        }


    public static void read(OnDataLoadedListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("CalendarEntities");
        final ArrayList<CalendarEntity> entities = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CalendarEntity entity = postSnapshot.getValue(CalendarEntity.class);
                    entities.add(entity);
                }
                listener.onDataLoaded(entities); // Notify the listener
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read data from firebase", databaseError.toException());
            }
        };
        mDatabase.addListenerForSingleValueEvent(valueEventListener);
    }
    public interface OnDataLoadedListener {
        void onDataLoaded(ArrayList<CalendarEntity> entities);
    }


}
