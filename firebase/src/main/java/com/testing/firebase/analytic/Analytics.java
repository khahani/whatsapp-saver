package com.testing.firebase.analytic;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics extends AnalyticsBase {

    private FirebaseAnalytics mFirebaseAnalytics;

    public Analytics(Context context) {
        super(context);
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

}
