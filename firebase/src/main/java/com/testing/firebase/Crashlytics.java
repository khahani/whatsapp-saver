package com.testing.firebase;

import com.khahani.usecase_firebase.CrashlyticsBase;

public class Crashlytics extends CrashlyticsBase {
    @Override
    public void run() {
        if (BuildConfig.DEBUG)
            throw new RuntimeException("Test Crash");
    }
}
