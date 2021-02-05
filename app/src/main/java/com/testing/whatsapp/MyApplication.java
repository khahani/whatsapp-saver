package com.testing.whatsapp;

import androidx.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {
    private String filters;

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public static final String DEFAULT_FILTERS = "[\n" +
            "  {\n" +
            "    \"lan\": \"en\",\n" +
            "    \"invalidSenders\": [\n" +
            "      \"WhatsApp\",\n" +
            "      \"null\"\n" +
            "    ],\n" +
            "    \"regexSenderValidator\": [\n" +
            "      \"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed voice calls\",\n" +
            "      \"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed video calls\",\n" +
            "      \"\\\\(messages ([0-9]|[0-9][0-9]|[0-9][0-9][0-9])\\\\)\"\n" +
            "    ],\n" +
            "    \"invalidMessages\": [\n" +
            "      \"null\",\n" +
            "      \"Checking for new messages\",\n" +
            "      \"Incoming voice call\",\n" +
            "      \"Incoming video call\",\n" +
            "      \"Missed voice call\",\n" +
            "      \"Missed video call\",\n" +
            "      \"\\uD83D\\uDCF7 Photo\",\n" +
            "      \"Calling…\",\n" +
            "      \"Ringing…\",\n" +
            "      \"Ongoing voice call\",\n" +
            "      \"Ongoing video call\",\n" +
            "      \"\\uD83D\\uDCF9 Incoming video call\"\n" +
            "    ],\n" +
            "    \"regexMessageValidators\": [\n" +
            "      \"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) new messages\",\n" +
            "      \"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed voice calls\",\n" +
            "      \"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed calls\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"lan\": \"fa\",\n" +
            "    \"invalidSenders\": [\n" +
            "      \"واتساپ\",\n" +
            "      \"null\"\n" +
            "    ],\n" +
            "    \"regexSenderValidator\": [\n" +
            "      \"\"\n" +
            "    ],\n" +
            "    \"invalidMessages\": [\n" +
            "      \"\"\n" +
            "    ],\n" +
            "    \"regexMessageValidators\": [\n" +
            "      \"\\u200F\\u200F([0-9]|[0-9][0-9]|[0-9][0-9][0-9]) پيام در ([0-9]|[0-9][0-9]|[0-9][0-9][0-9]) گفتگو\",\n" +
            "      \"\"\n" +
            "    ]\n" +
            "  }\n" +
            "]";
}
