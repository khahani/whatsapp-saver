package com.testing.whatsapp.db.utils;

import com.testing.whatsapp.db.Db;
import com.testing.whatsapp.db.ReceivedMessage;

public class ChatToDbMediator {
    protected final Db db;

    public ChatToDbMediator(Db db) {
        this.db = db;
    }


    public void setChat(ReceivedMessage receivedMessage) {

    }
}
