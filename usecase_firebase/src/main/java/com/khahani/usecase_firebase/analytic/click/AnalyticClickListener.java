package com.khahani.usecase_firebase.analytic.click;

import com.khahani.usecase_firebase.analytic.AnalyticsBase;

public abstract class AnalyticClickListener {
    protected final AnalyticsBase analytics;

    public AnalyticClickListener(AnalyticsBase analytics) {
        this.analytics = analytics;
    }

    protected abstract String getName();

    protected abstract String getId();

    protected abstract TrackClick.Type getType();

    protected void trackClick() {
        TrackClick trackClick = new TrackClick(analytics, getId(), getName(), getType());
        trackClick.run();
    }
}
