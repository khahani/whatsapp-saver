package com.testing.firebase.analytic;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics implements Runnable, LogEvent {

    private final Context context;
    private FirebaseAnalytics mFirebaseAnalytics;

    public Analytics(Context context) {
        this.context = context;
    }

    private void enable() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public void run() {
        enable();
    }

    @Override
    public void logEvent(String key, Bundle value) {
        mFirebaseAnalytics.logEvent(key, value);
    }

    public static class Param {
        public static final String SCREEN_NAME = "screen_name";
        public static final String SCREEN_CLASS = "screen_class";
        public static final String ITEM_ID = "item_id";
        public static final String ITEM_NAME = "item_name";
        public static final String CONTENT_TYPE = "content_type";
    }

    public static class Event {
        public static final String SCREEN_VIEW = "screen_view";
        public static final String SELECTED_ITEM = "select_item";
    }
}
