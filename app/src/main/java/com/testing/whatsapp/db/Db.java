package com.testing.whatsapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ReceivedMessage.class, Chat.class}, version = 1, exportSchema = false)
public abstract class Db extends RoomDatabase {

    private static Db instance;

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE ReceivedMessage ADD COLUMN 'group' TEXT DEFAULT 'c'");
//        }
//    };
//    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE ReceivedMessage ADD COLUMN 'posttime' INTEGER ");
//        }
//    };

    public abstract ReceivedMessageDao receivedMessageDao();

    public abstract ChatDao chatDao();

    public static synchronized Db getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, Db.class, "db")
                    .allowMainThreadQueries()
                    //.fallbackToDestructiveMigration()
//                    .addMigrations(MIGRATION_1_2)
//                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return instance;
    }
}
