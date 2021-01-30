package com.testing.whatsapp;


import org.junit.Test;

import java.util.ArrayList;

public class FilterNotificationTest {

    @Test
    public void test() {

        ArrayList<Message> possibleMessages = new ArrayList<>();
        possibleMessages.add(new Message("E", "Hi, I love you"));
        possibleMessages.add(new Message("E", "2 new messages"));
        possibleMessages.add(new Message("WhatsApp", "3 messages from 2 chats"));
        possibleMessages.add(new Message(null, null));
        possibleMessages.add(new Message("+98 902 570 4390", "سجاد"));
        possibleMessages.add(new Message("WhatsApp", "3 messages from 3 chats"));
        possibleMessages.add(new Message("WhatsApp", "Checking for new messages"));
        possibleMessages.add(new Message("+98 902 570 4390", "Incoming voice call"));
        possibleMessages.add(new Message("+98 902 570 4390", "Incoming video call"));
        possibleMessages.add(new Message("+98 902 570 4390", "Missed voice call"));
        possibleMessages.add(new Message("+98 902 570 4390", "Missed video call"));
        possibleMessages.add(new Message("سجاد", "\uD83C\uDFA4 Voice message (0:06)"));
        possibleMessages.add(new Message("+98 902 570 4390", "\uD83D\uDCF7 Photo"));
        possibleMessages.add(new Message("+98 902 570 4390", "\uD83D\uDE02\uD83D\uDE02")); // :D:D
        possibleMessages.add(new Message("+98 902 570 4390", "\uD83D\uDC9F Sticker")); // Big sticker
        possibleMessages.add(new Message("+98 902 570 4390", "\uD83C\uDFA5 Video (0:36)"));
        possibleMessages.add(new Message("مامان", "Calling…"));
        possibleMessages.add(new Message("مامان", "Ringing…"));
        possibleMessages.add(new Message("مامان", "Ongoing voice call"));
        possibleMessages.add(new Message("مامان", "Ongoing video call"));
        possibleMessages.add(new Message("E", "https://upmedia.me/"));
        possibleMessages.add(new Message("واتساپ", "\u200F\u200F۲ پيام در ۲ گفتگو"));
        possibleMessages.add(new Message("واتساپ", "\u200F\u200F۳ پيام در ۲ گفتگو"));
        possibleMessages.add(new Message("E @ Thebook", "\uD83D\uDCF7 شیلنگ")); //Picture with caption in group
        possibleMessages.add(new Message("حسین داداشی @ Distance", "\uD83E\uDD26\u200D♂️\uD83E\uDD26\u200D♂️\uD83E\uDD26\u200D♂️☠"));
        possibleMessages.add(new Message("واتساب", "\u200F\u200F٥ رسائل من ٣ دردشات"));
        possibleMessages.add(new Message("E", "\u200Fمكالمة صوتية واردة"));
        possibleMessages.add(new Message("\u200Fمكالمتان صوتيتان فائتتان ٢", "\u200F\u200F\u202AE\u202C\u200F، \u200F\u202A+98 902 570 4390\u202C\u200F"));// When you have 2 missed calls from different contact
        possibleMessages.add(new Message("E", "\u200Fمكالمة صوتية جارية"));
        possibleMessages.add(new Message("", ""));
        // When there is some messages that didn't see (unread messages), all messages ITERATES when A new message arrived [duplicate possiblitity]

    }

    public class Message {
        private String sender;
        private String message;

        public Message(String sender, String message) {
            this.sender = sender;
            this.message = message;
        }


        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
