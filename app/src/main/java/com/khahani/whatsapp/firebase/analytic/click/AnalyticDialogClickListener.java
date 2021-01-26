package com.khahani.whatsapp.firebase.analytic.click;

import android.content.DialogInterface;

import com.khahani.whatsapp.firebase.analytic.Analytics;

public abstract class AnalyticDialogClickListener implements DialogInterface.OnClickListener {

    private final Analytics analytics;

    protected AnalyticDialogClickListener(Analytics analytics) {
        this.analytics = analytics;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        TrackClick trackClick = new TrackClick(analytics, getId(), getName(), TrackClick.Type.Dialog);
        trackClick.run();
        this.click(dialog, which);
    }

    protected abstract String getName();

    protected abstract String getId();

    protected abstract void click(DialogInterface dialog, int which);
}
