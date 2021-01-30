package com.khahani.usecase_firebase.analytic.screen;

import android.os.Bundle;

import com.khahani.usecase_firebase.analytic.AnalyticsBase;
import com.khahani.usecase_firebase.analytic.LogEvent;


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
        bundle.putString(AnalyticsBase.Param.SCREEN_NAME, trackableScreen.getScreenName());
        bundle.putString(AnalyticsBase.Param.SCREEN_CLASS, trackableScreen.getClassName());
        logger.logEvent(AnalyticsBase.Event.SCREEN_VIEW, bundle);
    }
}
