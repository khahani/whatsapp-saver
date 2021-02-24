package com.whatsappear.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Chat {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    public int cid;

    @ColumnInfo(name = "group")
    public String group;

    @ColumnInfo(name = "sender")
    public String sender;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "date")
    public long date;

    public boolean isContact() {
        return group.equals("c");
    }
}
