package com.whatsappear.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReceivedMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMessage(ReceivedMessage message);

    @Query("Select * from receivedmessage where sender = :pSender and `group` = 'c' order by date asc")
    LiveData<List<ReceivedMessage>> getMessages(String pSender);

    @Query("Select * from receivedmessage where `group` = :pGroup order by date asc")
    LiveData<List<ReceivedMessage>> getMessagesByGroup(String pGroup);

    @Query("Select * from receivedmessage where sender = :pSender and `group` = 'c' order by date asc limit 50")
    List<ReceivedMessage> getMessagesSync(String pSender);
}
