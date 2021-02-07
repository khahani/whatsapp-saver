package com.testing.whatsapp.db.adapter;

import androidx.annotation.NonNull;

import com.testing.whatsapp.Model.Chat;
import com.testing.whatsapp.db.ReceivedMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;

public class ChatAdapterTest {

    private ChatAdapter chatAdapter;

    @Before
    public void setUp() {
        chatAdapter = new ChatAdapter();
    }

    @Test
    public void empty() {
        ReceivedMessage receivedMessage = new ReceivedMessage();
        receivedMessage.sender = "A";
        receivedMessage.group = "c";
        receivedMessage.text = "Hi";
        receivedMessage.date = 1612711761281L;
        String dateStr = "6:59 PM";

        Chat chat = getChat(receivedMessage);

        Assert.assertEquals(receivedMessage.sender, chat.getName());
        Assert.assertEquals(receivedMessage.group, chat.getGroup());
        Assert.assertEquals(dateStr, chat.getLastMessageTime());
        Assert.assertEquals(receivedMessage.text, chat.getLastMessage());
    }

    @NonNull
    private Chat getChat(ReceivedMessage receivedMessage) {
        Chat chat = new Chat();
        chat.setName(receivedMessage.sender);
        chat.setLastMessage(receivedMessage.text);
        chat.setGroup(receivedMessage.group);
        DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
        chat.setLastMessageTime(df.format(receivedMessage.date));
        return chat;
    }
}