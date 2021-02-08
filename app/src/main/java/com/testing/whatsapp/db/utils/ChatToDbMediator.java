package com.testing.whatsapp.db.utils;

import com.testing.whatsapp.db.Chat;
import com.testing.whatsapp.db.Db;
import com.testing.whatsapp.db.ReceivedMessage;

import java.util.List;

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
        execute();
    }

    private void execute() {
        List<Chat> chats = db.chatDao().getAll();

        long duplicatedId = -1;
        if (receivedMessage.isContact()) {
            for (Chat c : chats) {
                if (c.sender.equals(receivedMessage.sender)) {
                    duplicatedId = c.cid;
                    break;
                }
            }
        } else {
            for (Chat c : chats) {
                if (c.group.equals(receivedMessage.group)) {
                    duplicatedId = c.cid;
                    break;
                }
            }
        }


        if (duplicatedId >= 0) {
            db.chatDao().update(chat, duplicatedId);
        } else {
            db.chatDao().insert(chat);
        }
    }
}
