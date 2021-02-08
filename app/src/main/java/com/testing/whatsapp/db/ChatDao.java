package com.testing.whatsapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Chat chat);

    @Query("Select * from chat order by date desc")
    LiveData<List<Chat>> getAll();

    @Query("Select * from chat")
    List<Chat> getAllSync();

    @Update
    void update(Chat chat);
}
