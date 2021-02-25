package com.whatsappear.db.utils;

import com.khahani.usecase_firebase.performance.Performancable;
import com.khahani.usecase_firebase.performance.TrackerKeyMaker;
import com.whatsappear.db.Chat;
import com.whatsappear.db.Db;
import com.whatsappear.db.ReceivedMessage;

import java.util.List;

public class ChatToDbMediator implements Performancable {
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

        List<Chat> chats = db.chatDao().getAllSync();

        long duplicatedId = -1;
        if (receivedMessage.isContact()) {
            for (Chat c : chats) {
                if (c.sender.equals(receivedMessage.sender) && c.isContact()) {
                    duplicatedId = c.cid;
                    break;
                }
            }
        } else {
            for (Chat c : chats) {
                if (c.group.equals(receivedMessage.group) && !c.isContact()) {
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

        if (duplicatedId >= 1) {
            chat.cid = (int) duplicatedId;
            db.chatDao().update(chat);
        } else {
            db.chatDao().insert(chat);
        }
    }

    @Override
    public String getTrackerKey() {
        TrackerKeyMaker t = TrackerKeyMaker.getInstance(this, "run");
        t.run();
        return t.getKey();
    }
}
