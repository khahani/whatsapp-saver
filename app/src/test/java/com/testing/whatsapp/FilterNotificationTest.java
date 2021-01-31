package com.testing.whatsapp;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class FilterNotificationTest {

    private ArrayList<Message> possibleMessages;

    @Before
    public void setup() {
        initData();
    }

    @Test
    public void sender_test() {

        String[] invalidSenders = new String[]{
                "WhatsApp", // and translated in different languages
                "null"
        };

        String[] invalidSenderRegex = new String[]{
                "2 missed voice calls", // and translated
                "2 missed video calls" // if there is any (should test)
        };

        // if sender is one of invalidSenders should reject

        String[] group = new String[]{
                "E @ Thebook",
                "حسین داداشی @ Distance",
        };

        // if sender contains @ then left side is the sender and right side is the group

    }

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

    private void initData() {
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
        possibleMessages.add(new Message("E", "2 missed voice calls"));
        possibleMessages.add(new Message("E", "3 missed calls"));
        possibleMessages.add(new Message("سجاد", "\uD83C\uDFA4 Voice message (0:06)"));
        possibleMessages.add(new Message("+98 902 570 4390", "\uD83D\uDCF7 Photo"));
        possibleMessages.add(new Message("+98 902 570 4390", "\uD83D\uDE02\uD83D\uDE02")); // :D:D
        possibleMessages.add(new Message("+98 902 570 4390", "\uD83D\uDC9F Sticker")); // Big sticker
        possibleMessages.add(new Message("+98 902 570 4390", "\uD83C\uDFA5 Video (0:36)"));
        possibleMessages.add(new Message("مامان", "Calling…"));
        possibleMessages.add(new Message("مامان", "Ringing…"));
        possibleMessages.add(new Message("مامان", "Ongoing voice call"));
        possibleMessages.add(new Message("مامان", "Ongoing video call"));
        possibleMessages.add(new Message("E", "\uD83D\uDCF9 Incoming video call"));
        possibleMessages.add(new Message("E", "Missed video call"));
        possibleMessages.add(new Message("2 missed voice calls", "E, +98 902 570 4390"));
        possibleMessages.add(new Message("E", "https://upmedia.me/"));
        possibleMessages.add(new Message("واتساپ", "\u200F\u200F۲ پيام در ۲ گفتگو"));
        possibleMessages.add(new Message("واتساپ", "\u200F\u200F۳ پيام در ۲ گفتگو"));
        possibleMessages.add(new Message("E @ Thebook", "\uD83D\uDCF7 شیلنگ")); //Picture with caption in group
        possibleMessages.add(new Message("حسین داداشی @ Distance", "\uD83E\uDD26\u200D♂️\uD83E\uDD26\u200D♂️\uD83E\uDD26\u200D♂️☠"));
        possibleMessages.add(new Message("واتساب", "\u200F\u200F٥ رسائل من ٣ دردشات"));
        possibleMessages.add(new Message("E", "\u200Fمكالمة صوتية واردة"));
        possibleMessages.add(new Message("\u200Fمكالمتان صوتيتان فائتتان ٢", "\u200F\u200F\u202AE\u202C\u200F، \u200F\u202A+98 902 570 4390\u202C\u200F"));// When you have 2 missed calls from different contact
        possibleMessages.add(new Message("E", "\u200Fمكالمة صوتية جارية"));
        possibleMessages.add(new Message("حسین جوان بادصبا @ \uD83D\uDE80 موج همراه \uD83C\uDFE9", "مبارکه. \n" +
                "فردا جهت تست نرم افزار گوشی شما به واحد پشتیبانی منتقل میشه\uD83D\uDE01"));
        possibleMessages.add(new Message("WhatsApp", "12 messages from 2 chats"));
        possibleMessages.add(new Message("", ""));
        possibleMessages.add(new Message("", ""));
        possibleMessages.add(new Message("", ""));
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
