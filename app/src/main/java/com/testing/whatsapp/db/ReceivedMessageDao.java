package com.testing.whatsapp.db;

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

    @Query("Select r._id, r.sender, (select text from receivedmessage where sender = r.sender order by date desc limit 1) as text, (select date from receivedmessage where sender = r.sender order by date desc limit 1) as date from receivedmessage as r group by sender order by date desc ")
    LiveData<List<ReceivedMessage>> getSenders();

    @Query("Select * from receivedmessage where sender = :pSender order by date asc")
    LiveData<List<ReceivedMessage>> getChats(String pSender);

    @Query("Select count(*) from (Select * from receivedmessage where sender = :pSender order by date desc limit 20) as r  where r.sender = :pSender and r.text = :pText and r.date < :pDate - 2000")
    int messageIsDuplicate(String pSender, String pText, long pDate);


}
