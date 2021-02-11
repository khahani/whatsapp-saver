package com.testing.whatsapp.db.utils;

import com.khahani.usecase_firebase.performance.Performance;
import com.khahani.usecase_firebase.performance.Trace;
import com.testing.firebase.performance.PerformanceImpl;
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
        Performance p = new PerformanceImpl();
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Trace t = p.newTrace(this.getClass().getName() + "." + methodName + "()");
        t.start();
        execute();
        t.stop();
    }

    private void execute() {

        List<Chat> chats = db.chatDao().getAllSync();

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

        Chat chat = new Chat();
        chat.sender = receivedMessage.sender;
        chat.text = receivedMessage.text;
        chat.group = receivedMessage.group;
        chat.date = receivedMessage.date;

        if (duplicatedId >= 0) {
            chat.cid = (int) duplicatedId;
            db.chatDao().update(chat);
        } else {
            db.chatDao().insert(chat);
        }
    }
}
