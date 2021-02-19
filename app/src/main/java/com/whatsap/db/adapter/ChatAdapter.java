package com.whatsap.db.adapter;

import com.whatsap.Model.Chat;
import com.whatsap.db.ReceivedMessage;

import java.text.DateFormat;

public class ChatAdapter {

    public Chat getChat(ReceivedMessage receivedMessage) {
        Chat chat = new Chat();
        chat.setName(receivedMessage.sender);
        chat.setLastMessage(receivedMessage.text);
        chat.setGroup(receivedMessage.group);
        DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
        chat.setLastMessageTime(df.format(receivedMessage.date));
        return chat;
    }

}
