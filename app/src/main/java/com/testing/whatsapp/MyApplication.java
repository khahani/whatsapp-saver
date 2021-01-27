package com.testing.whatsapp;

import androidx.multidex.MultiDexApplication;

import com.testing.whatsapp.firebase.InAppMessage;

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        new InAppMessage().run();
    }
}
