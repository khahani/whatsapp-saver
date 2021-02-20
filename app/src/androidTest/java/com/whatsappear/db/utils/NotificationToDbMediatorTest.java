package com.whatsappear.db.utils;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.whatsappear.db.Db;
import com.whatsappear.db.ReceivedMessageDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NotificationToDbMediatorTest {

    private Db db;
    private ReceivedMessageDao dao;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, Db.class).build();
        dao = db.receivedMessageDao();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void insert() {

    }
}