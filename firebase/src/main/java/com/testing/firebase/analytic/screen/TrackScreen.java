package com.testing.firebase.analytic.screen;

import android.os.Bundle;

import com.testing.firebase.analytic.Analytics;
import com.testing.firebase.analytic.LogEvent;


public class TrackScreen implements Runnable {

    private final LogEvent logger;
    private final TrackableScreen trackableScreen;

    public TrackScreen(LogEvent logger, TrackableScreen trackableScreen) {
        this.logger = logger;
        this.trackableScreen = trackableScreen;
    }

    @Override
    public void run() {
        logEvent();
    }

    private void logEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(Analytics.Param.SCREEN_NAME, trackableScreen.getScreenName());
        bundle.putString(Analytics.Param.SCREEN_CLASS, trackableScreen.getClassName());
        logger.logEvent(Analytics.Event.SCREEN_VIEW, bundle);
    }
}
