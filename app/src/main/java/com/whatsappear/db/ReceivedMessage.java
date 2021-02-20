package com.whatsappear.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"posttime"}, unique = true)})
public class ReceivedMessage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    public int rid;

    @ColumnInfo(name = "group")
    public String group;

    @ColumnInfo(name = "sender")
    public String sender;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    public byte[] image;

    @ColumnInfo(name = "date")
    public long date;

    @ColumnInfo(name = "posttime")
    public long postTime;

    public boolean isContact() {
        return group.equals("c");
    }
}
