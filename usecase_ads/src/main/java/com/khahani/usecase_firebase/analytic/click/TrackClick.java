package com.khahani.usecase_firebase.analytic.click;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.khahani.usecase_firebase.analytic.Analytics;
import com.khahani.usecase_firebase.analytic.LogEvent;

public class TrackClick implements Runnable {

    private final LogEvent logger;
    private final String id;
    private final String name;
    private final Type type;

    public TrackClick(LogEvent logger, String id, String name, Type type) {
        this.logger = logger;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Override
    public void run() {
        logEvent();
    }

    private void logEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(Analytics.Param.ITEM_ID, id);
        bundle.putString(Analytics.Param.ITEM_NAME, name);
        bundle.putString(Analytics.Param.CONTENT_TYPE, type.toString());
        logger.logEvent(Analytics.Event.SELECTED_ITEM, bundle);
    }

    public enum Type {
        DialogButton("Dialog_button"),
        DialogDismiss("Dialog_dismiss"),
        ToolbarButton("Toolbar_button"),
        AppodealInterstitial("Appdeal_Interstitial"),
        AppdealBanner("Appdeal_Banner");

        private final String type;

        Type(final String type) {
            this.type = type;
        }

        @NonNull
        @Override
        public String toString() {
            return type;
        }
    }
}
