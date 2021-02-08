package com.testing.whatsapp.db.utils;

import com.testing.whatsapp.db.Db;
import com.testing.whatsapp.db.ReceivedMessage;

public class ChatToDbMediator implements Runnable {
    protected final Db db;
    private ReceivedMessage receivedMessage;

    public ChatToDbMediator(Db db) {
        this.db = db;
    }

    public void setChat(ReceivedMessage receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    @Override
    public void run() {

    }
}
