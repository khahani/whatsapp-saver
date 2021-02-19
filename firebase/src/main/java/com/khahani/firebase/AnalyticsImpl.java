package com.khahani.firebase;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.khahani.usecase_firebase.analytic.Analytics;

public class AnalyticsImpl extends Analytics {

    private FirebaseAnalytics mFirebaseAnalytics;

    public AnalyticsImpl(Context context) {
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
