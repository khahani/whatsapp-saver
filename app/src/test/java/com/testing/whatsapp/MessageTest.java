package com.testing.whatsapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageTest {

    private final String[] invalidMessagesRegexRequired = new String[]{
            "2 new messages",
            //"3 messages from 2 chats", // the sender of this message is whatsapp so by default will removes.
            "2 missed voice calls",
            "3 missed calls"
    };
    private ArrayList<String> wrongMessagesFakeData;
    private String[] invalidMessages;
    private String[] regexMessageValidators;
    private String message;

    @Before
    public void construct() {
        // these messages should check with corresponding translated one.
        invalidMessages = new String[]{
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
                "\uD83D\uDCF9 Incoming video call"
//                ,
//                "\uD83C\uDFA4 Voice message (0:06)",
//                "\uD83C\uDFA5 Video (0:36)"
        };

        regexMessageValidators = new String[]{
                "([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) new messages",
                "([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed voice calls",
                "([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed calls",
        };

        wrongMessagesFakeData = new ArrayList<>();
        wrongMessagesFakeData.addAll(Arrays.asList(invalidMessages));
        wrongMessagesFakeData.addAll(Arrays.asList(invalidMessagesRegexRequired));
    }

    @Test
    public void check_message_is_valid() {
        for (int i = 0; i < wrongMessagesFakeData.size(); i++) {
            message = wrongMessagesFakeData.get(i);
            assertMessageIsValid();
        }
    }

    private void assertMessageIsValid() {
        boolean isInvalidMessage = true;

        for (String iv : invalidMessages) {
            if (message.equals(iv)) {
                isInvalidMessage = false;
                break;
            }
        }

        boolean isInvalidPatternMessage = true;

        for (String regx : regexMessageValidators) {
            Pattern p = Pattern.compile(regx);
            Matcher m = p.matcher(message);
            if (m.find()) {
                isInvalidPatternMessage = false;
                break;
            }
        }

        boolean isValid = isInvalidMessage && isInvalidPatternMessage;

        Assert.assertEquals(String.format("Message: %s", message), false, isValid);
    }
}
