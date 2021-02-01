package com.testing.whatsapp;

import org.junit.Assert;
import org.junit.Test;

public class GroupTest {

    @Test
    public void when_a_sender_contain_at_sign_then_is_group() {

        String[] senders = new String[]{
                "E @ Thebook",
                "حسین داداشی @ Distance",
        };

        for (String sender : senders) {
            assertIsGroup(sender);
        }

    }

    private void assertIsGroup(String sender) {
        Assert.assertTrue(String.format("sender:%s", sender), isGroup(sender));
    }

    private boolean isGroup(String sender) {
        return sender.contains("@");
    }

}