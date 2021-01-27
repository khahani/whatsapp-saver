package com.testing.whatsapp.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.testing.whatsapp.firebase.Crashlytics;
import com.testing.whatsapp.firebase.RemoteConfig;
import com.testing.whatsapp.firebase.analytic.Analytics;
import com.testing.whatsapp.firebase.analytic.LogEvent;
import com.testing.whatsapp.firebase.analytic.screen.TrackScreen;
import com.testing.whatsapp.firebase.analytic.screen.TrackableScreen;

public abstract class BaseActivity extends AppCompatActivity implements TrackableScreen {

    private Analytics analytic;
    private RemoteConfig remoteConfig;
    private Crashlytics crashlytics;
    protected TrackScreen trackScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crashlytics = new Crashlytics();
        analytic = new Analytics(this);
        remoteConfig = new RemoteConfig();
        trackScreen = initTrackScreen(analytic);
        analytic.run();
        remoteConfig.run();
        crashlytics.run();
        trackScreen.run();
    }

    protected abstract TrackScreen initTrackScreen(LogEvent logger);

    public Analytics getAnalytic() {
        return analytic;
    }
}
