package com.khahani.whatsapp.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.khahani.whatsapp.firebase.Crashlytics;
import com.khahani.whatsapp.firebase.RemoteConfig;
import com.khahani.whatsapp.firebase.analytic.Analytics;
import com.khahani.whatsapp.firebase.analytic.screen.TrackableScreen;

public abstract class BaseActivity extends AppCompatActivity implements TrackableScreen {

    private Analytics analytic;
    private RemoteConfig remoteConfig;
    private Crashlytics crashlytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crashlytics = new Crashlytics();
        analytic = new Analytics(this);
        remoteConfig = new RemoteConfig();
        analytic.run();
        remoteConfig.run();
        crashlytics.run();
    }
}
