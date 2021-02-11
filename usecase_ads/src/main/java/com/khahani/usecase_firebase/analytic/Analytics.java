package com.khahani.usecase_firebase.analytic;

import android.content.Context;

public abstract class Analytics implements Runnable, LogEvent {
    protected final Context context;

    public Analytics(Context context) {
        this.context = context;
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
