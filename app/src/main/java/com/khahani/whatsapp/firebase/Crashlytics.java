package com.khahani.whatsapp.firebase;

import com.khahani.whatsapp.BuildConfig;

public class Crashlytics implements Runnable {
    @Override
    public void run() {
        if (BuildConfig.DEBUG)
            throw new RuntimeException();
    }
}
