package com.testing.whatsapp.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

public class RemoteConfigDefault {
    private final Context context;
    private final String FILTERS_KEY = "filters";
    private final String FILTER = "filter";
    private final String DEFAULT_FILTER = "[{\"lan\":\"en\",\"invalidSenders\":[\"WhatsApp\",\"null\"],\"regexSenderValidator\":[\"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed voice calls\",\"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed video calls\",\"\\\\(messages ([0-9]|[0-9][0-9]|[0-9][0-9][0-9])\\\\)\"],\"invalidMessages\":[\"null\",\"Checking for new messages\",\"Incoming voice call\",\"Incoming video call\",\"Missed voice call\",\"Missed video call\",\"\uD83D\uDCF7 Photo\",\"Calling…\",\"Ringing…\",\"Ongoing voice call\",\"Ongoing video call\",\"\uD83D\uDCF9 Incoming video call\"],\"regexMessageValidators\":[\"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) new messages\",\"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed voice calls\",\"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed calls\"]},{\"lan\":\"fa\",\"invalidSenders\":[\"واتساپ\",\"null\"],\"regexSenderValidator\":[\"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed voice calls\",\"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed video calls\",\"\\\\(messages ([0-9]|[0-9][0-9]|[0-9][0-9][0-9])\\\\)\"],\"invalidMessages\":[\"جستجو برای پیام جدید\",\"تماس صوتی ورودی\",\"تماس صوتی پاسخ داده نشده\",\"تماس تصویری ورودی\",\"تماس تصویری پاسخ داده نشده\"],\"regexMessageValidators\":[\"([0-9]|[0-9][0-9]|[0-9][0-9][0-9]) پيام در ([0-9]|[0-9][0-9]|[0-9][0-9][0-9]) گفتگو\",\"([0-9]|[0-9][0-9]|[0-9][0-9][0-9]) تماس صوتی پاسخ داده نشده\",\"([0-9]|[0-9][0-9]|[0-9][0-9][0-9]) تماس از دست رفته\",\"([0-9]|[0-9][0-9]|[0-9][0-9][0-9]) تماس تصویری پاسخ داده نشده\",\"([0-9]|[0-9][0-9]|[0-9][0-9][0-9]) پیام جدید\"]}]";

    public RemoteConfigDefault(Context context) {
        this.context = context;
    }

    public String getFilters() {
        return getSharedPreferences().getString(FILTER, DEFAULT_FILTER);
    }

    public void setFilters(String filters) {
        getSharedPreferences().edit().putString(FILTER, filters).apply();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(
                FILTERS_KEY, Context.MODE_PRIVATE);
    }
}
