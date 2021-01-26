package com.khahani.whatsapp.firebase;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics implements Runnable {

    private final Context context;
    private FirebaseAnalytics mFirebaseAnalytics;

    public Analytics(Context context) {
        this.context = context;
    }

    private void enable() {
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public void run() {
        enable();
    }
}
