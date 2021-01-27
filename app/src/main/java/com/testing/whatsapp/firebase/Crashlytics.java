package com.testing.whatsapp.firebase;

import com.testing.whatsapp.BuildConfig;

public class Crashlytics implements Runnable {
    @Override
    public void run() {
        if (BuildConfig.DEBUG)
            throw new RuntimeException("Test Crash");
    }
}
