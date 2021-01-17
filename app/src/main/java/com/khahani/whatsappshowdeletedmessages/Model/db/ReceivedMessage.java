package com.khahani.whatsappshowdeletedmessages.Model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
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
}
