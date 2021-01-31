package com.testing.firebase;

import com.khahani.usecase_firebase.CrashlyticsBase;

public class CrashlyticsImpl extends CrashlyticsBase {
    @Override
    public void run() {
        if (BuildConfig.DEBUG)
            throw new RuntimeException("Test Crash");
    }
}
