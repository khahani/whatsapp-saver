package com.khahani.whatsapp.firebase.analytic;

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
        public static final String SCREEN_NAME = FirebaseAnalytics.Param.SCREEN_NAME;
        public static final String SCREEN_CLASS = FirebaseAnalytics.Param.SCREEN_CLASS;
    }

    public static class Event {
        public static final String SCREEN_VIEW = FirebaseAnalytics.Event.SCREEN_VIEW;
        private static final String SELECTED_ITEM = FirebaseAnalytics.Event.SELECT_ITEM;
    }
}
