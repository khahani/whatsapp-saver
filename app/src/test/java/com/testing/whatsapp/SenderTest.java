package com.testing.whatsapp;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SenderTest {

    private ArrayList<Message> possibleMessages;

    String[] wrongSendersFakeData = new String[]{
            "WhatsApp", // and translated in different languages
            "null",
            "2 missed voice calls", // and translated
            "2 missed video calls", // if there is any (should test)
            "10 missed video calls",
            "152 missed video calls",
            "999 missed video calls",
            "(2 messages) E"
    };

    private void assertWrongSender(String sender) {
        boolean isValid = isValidSender(sender);
        Assert.assertFalse(String.format("sender: %s", sender), isValid);
    }

    @Before
    public void constuct() {
        regexSenderValidator = new String[]{
                "([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed voice calls",
                "([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed video calls",
                "\\(messages ([0-9]|[0-9][0-9]|[0-9][0-9][0-9])\\)"
        };

        invalidSenders = new String[]{
                "WhatsApp", // and translated in different languages
                "null"
        };
    }

    @Test
    public void wrong_sender_test() {
        for (String sender : wrongSendersFakeData) {
            assertWrongSender(sender);
        }
    }

    private String[] regexSenderValidator;
    private String[] invalidSenders;

    private boolean isValidSender(String sender) {
        return checkWithFixedValues(sender) && checkWithRegexValues(sender);
    }

    private boolean checkWithRegexValues(String sender) {
        for (String regx : regexSenderValidator) {
            Pattern p = Pattern.compile(regx);
            Matcher m = p.matcher(sender);
            if (m.find()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkWithFixedValues(String sender) {
        for (String invalidSender : invalidSenders) {
            if (sender.equals(invalidSender)) {
                break;
            }
        }
        return false;
    }

    private void initData() {
        possibleMessages = new ArrayList<>();
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
        possibleMessages.add(new Message("(3 messages) E", ""));//khahani: this should be check with real device
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
