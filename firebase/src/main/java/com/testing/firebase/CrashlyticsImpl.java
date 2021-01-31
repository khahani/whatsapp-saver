package com.testing.firebase;

import com.khahani.usecase_firebase.Crashlytics;

public class CrashlyticsImpl extends Crashlytics {
    @Override
    public void run() {
        if (BuildConfig.DEBUG)
            throw new RuntimeException("Test Crash");
    }
}
