package com.khahani.whatsappshowdeletedmessages.Model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ReceivedMessage.class}, version = 1, exportSchema = false)
public abstract class Db extends RoomDatabase {

    private static Db instance;

    public static synchronized Db getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, Db.class, "db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract ReceivedMessageDao receivedMessageDao();
}
