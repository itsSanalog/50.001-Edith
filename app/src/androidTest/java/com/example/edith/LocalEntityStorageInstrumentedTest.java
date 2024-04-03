package com.example.edith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.edith.models.CalendarEntity;
import com.example.edith.models.LocalEntityStorage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class LocalEntityStorageInstrumentedTest {

    private LocalEntityStorage localEntityStorage;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        localEntityStorage = new LocalEntityStorage(context);
    }

    @Test
    public void testCreateAndRead() {
        CalendarEntity entity = new CalendarEntity("Meeting", ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        localEntityStorage.create(entity);

        ArrayList<CalendarEntity> entities = localEntityStorage.read();
        assertTrue(entities.size() > 0);
        assertEquals("Meeting", entities.get(0).getEntityTitle());
    }

    @Test
    public void testUpdate() {
        CalendarEntity entity = new CalendarEntity("Meeting", ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        localEntityStorage.create(entity);

        entity.setEntityTitle("Updated Meeting");
        localEntityStorage.update(entity);

        ArrayList<CalendarEntity> entities = localEntityStorage.read();
        assertEquals("Updated Meeting", entities.get(1).getEntityTitle());
    }

    @Test
    public void testDelete() {
        ArrayList<CalendarEntity> entities = localEntityStorage.read();
        System.out.println(entities.size());
        CalendarEntity entity = new CalendarEntity("Meeting1", ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        System.out.println(entity.getEntityID());
        localEntityStorage.create(entity);
        System.out.println(entity.getEntityID());
        localEntityStorage.delete(entity.getEntityID());


        ArrayList<CalendarEntity> entities2 = localEntityStorage.read();
        System.out.println(entities2.size());
        assertEquals(entities.size(), entities2.size());
    }
}