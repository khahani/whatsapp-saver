package com.testing.whatsapp.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.testing.firebase.Crashlytics;
import com.testing.firebase.InAppMessage;
import com.testing.firebase.RemoteConfig;
import com.testing.firebase.analytic.Analytics;
import com.testing.firebase.analytic.LogEvent;
import com.testing.firebase.analytic.screen.TrackScreen;
import com.testing.firebase.analytic.screen.TrackableScreen;

public abstract class BaseActivity extends AppCompatActivity implements TrackableScreen {

    private Analytics analytic;
    private RemoteConfig remoteConfig;
    private Crashlytics crashlytics;
    protected TrackScreen trackScreen;
    private InAppMessage inAppMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crashlytics = new Crashlytics();
        analytic = new Analytics(this);
        remoteConfig = new RemoteConfig();
        trackScreen = initTrackScreen(analytic);
        analytic.run();
        remoteConfig.run();
//        crashlytics.run();
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

    public Analytics getAnalytic() {
        return analytic;
    }
}
