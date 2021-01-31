package com.testing.whatsapp.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.khahani.usecase_firebase.Crashlytics;
import com.khahani.usecase_firebase.InAppMessage;
import com.khahani.usecase_firebase.RemoteConfig;
import com.khahani.usecase_firebase.analytic.Analytics;
import com.khahani.usecase_firebase.analytic.LogEvent;
import com.khahani.usecase_firebase.analytic.screen.TrackScreen;
import com.khahani.usecase_firebase.analytic.screen.TrackableScreen;
import com.testing.whatsapp.creator.firebase.AnalyticsCreator;
import com.testing.whatsapp.creator.firebase.CrashlyticCreator;
import com.testing.whatsapp.creator.firebase.InAppMessageCreator;
import com.testing.whatsapp.creator.firebase.RemotConfigCreator;

public abstract class BaseActivity extends AppCompatActivity implements TrackableScreen {

    private Analytics analytic;
    private RemoteConfig remoteConfig;
    private Crashlytics crashlytics;
    private TrackScreen trackScreen;
    private InAppMessage inAppMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crashlytics = new CrashlyticCreator().factoryMethod();
        analytic = new AnalyticsCreator(this).factoryMethod();
        remoteConfig = new RemotConfigCreator().factoryMethod();
        trackScreen = initTrackScreen(analytic);
        analytic.run();
        remoteConfig.run();
        //crashlytics.run();
        trackScreen.run();
        //khahani: check why in app device id not shown in toast or log
        inAppMessage = new InAppMessageCreator().factoryMethod();
        inAppMessage.run();
    }

    protected abstract TrackScreen initTrackScreen(LogEvent logger);

    public Analytics getAnalytic() {
        return analytic;
    }
}
