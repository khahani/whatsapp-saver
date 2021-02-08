package com.testing.whatsapp.db;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

public interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Chat chat);

    @Query("Select * from chat order by date desc")
    List<Chat> getAll();
}
