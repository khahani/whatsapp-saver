package com.khahani.whatsappshowdeletedmessages.Model.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ReceivedMessage.class}, version = 2, exportSchema = false)
public abstract class Db extends RoomDatabase {

    private static Db instance;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ReceivedMessage ADD COLUMN 'group' TEXT DEFAULT 'c'");
        }
    };

    public abstract ReceivedMessageDao receivedMessageDao();

    public static synchronized Db getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, Db.class, "db")
                    //.fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }
}
