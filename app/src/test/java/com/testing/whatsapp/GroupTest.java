package com.testing.whatsapp;

import org.junit.Assert;
import org.junit.Test;

public class GroupTest {

    private final String[] titles = new String[]{
            "E @ Thebook",
            "حسین داداشی @ Distance",
    };

    private final String[] groups = new String[]{
            "Thebook",
            "Distance"
    };

    private final String[] senders = new String[]{
            "E",
            "حسین داداشی"
    };
    private String title;

    @Test
    public void when_a_title_contain_at_sign_then_is_group() {
        for (String title : titles) {
            this.title = title;
            assertIsGroup(title);
        }
    }

    private void assertIsGroup(String title) {
        Assert.assertTrue(String.format("sender:%s", title), isGroup());
    }

    @Test
    public void when_a_title_is_group_then_sender_and_group_extract_correctly() {
        for (int i = 0; i < titles.length; i++) {
            title = titles[i];
            if (isGroup()) {
                String sender = getSender();
                String group = getGroup();
                Assert.assertEquals(senders[i], sender);
                Assert.assertEquals(groups[i], group);
            }
        }
    }

    private String getGroup() {
        return title.split("@")[1].trim();
    }

    private String getSender() {
        return title.split("@")[0].trim();
    }

    private boolean isGroup() {
        return title.contains("@") && title.split("@").length == 2;
    }

}