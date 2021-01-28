package com.testing.firebase;

public class Crashlytics implements Runnable {
    @Override
    public void run() {
        if (BuildConfig.DEBUG)
            throw new RuntimeException("Test Crash");
    }
}
