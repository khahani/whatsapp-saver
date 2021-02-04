package com.khahani.extractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class FilterTest {

    private final String json = "[{\"lan\":\"en\",\"invalidSenders\":[\"WhatsApp\",\"null\"],\"regexSenderValidator\":[\"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed voice calls\",\"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed video calls\",\"\\\\(messages ([0-9]|[0-9][0-9]|[0-9][0-9][0-9])\\\\)\"],\"invalidMessages\":[\"null\",\"Checking for new messages\",\"Incoming voice call\",\"Incoming video call\",\"Missed voice call\",\"Missed video call\",\"\uD83D\uDCF7 Photo\",\"Calling…\",\"Ringing…\",\"Ongoing voice call\",\"Ongoing video call\",\"\uD83D\uDCF9 Incoming video call\"],\"invalidMessagesRegexRequired\":[\"2 new messages\",\"2 missed voice calls\",\"3 missed calls\"]}]";

    @Test
    public void extract_language_from_json_test() {

        String language = "";

        try {
            JSONArray root = new JSONArray(json);
            for (int i = 0; i < root.length(); i++) {
                JSONObject filter = root.getJSONObject(i);
                language = filter.getString("lan");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Assert.assertEquals("en", language);
    }
}
