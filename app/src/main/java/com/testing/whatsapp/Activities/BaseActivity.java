package com.testing.whatsapp.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.khahani.usecase_firebase.CrashlyticsBase;
import com.khahani.usecase_firebase.InAppMessageBase;
import com.khahani.usecase_firebase.RemoteConfigBase;
import com.khahani.usecase_firebase.analytic.AnalyticsBase;
import com.khahani.usecase_firebase.analytic.LogEvent;
import com.khahani.usecase_firebase.analytic.screen.TrackScreen;
import com.khahani.usecase_firebase.analytic.screen.TrackableScreen;
import com.testing.whatsapp.firebase.CrashlyticCreator;

public abstract class BaseActivity extends AppCompatActivity implements TrackableScreen {

    private AnalyticsBase analytic;
    private RemoteConfigBase remoteConfig;
    private CrashlyticsBase crashlytics;
    private TrackScreen trackScreen;
    private InAppMessageBase inAppMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crashlytics = new CrashlyticCreator().factoryMethod();
        analytic = new Analytics(this);
        remoteConfig = new RemoteConfig();
        trackScreen = initTrackScreen(analytic);
        analytic.run();
        remoteConfig.run();
        //crashlytics.run();
        trackScreen.run();
        //khahani: check why in app device id not shown in toast or log
        inAppMessage = new InAppMessage(() -> {
            String deviceId = inAppMessage.getDeviceId();
            Log.d("Khahani", deviceId);
            Toast.makeText(getApplicationContext(), deviceId, Toast.LENGTH_LONG).show();
        });
        inAppMessage.run();
    }

    protected abstract TrackScreen initTrackScreen(LogEvent logger);

    public AnalyticsBase getAnalytic() {
        return analytic;
    }
}
