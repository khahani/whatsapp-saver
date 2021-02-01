package com.testing.whatsapp;

import org.junit.Test;

public class MessageTest {


    @Test
    public void message_test() {
        // these messages should check with corresponding translated one.
        String[] invalidMessages = new String[]{
                "null",
                "Checking for new messages",// the sender of this message is whatsapp so by default will removes.
                "Incoming voice call",
                "Incoming video call",
                "Missed voice call",
                "Missed video call",
                "\uD83D\uDCF7 Photo",
                "Calling…",
                "Ringing…",
                "Ongoing voice call",
                "Ongoing video call",
                "\uD83D\uDCF9 Incoming video call",
                "E, +98 902 570 4390" //this message show up when the sender is "2 missed voice calls" (for video must test)
        };

        String[] invalidMessagesRegexRequired = new String[]{
                "2 new messages",
                "3 messages from 2 chats", // the sender of this message is whatsapp so by default will removes.
                "2 missed voice calls",
                "3 missed calls",
                "\uD83C\uDFA4 Voice message (0:06)",
                "\uD83C\uDFA5 Video (0:36)"
        };
    }
}
