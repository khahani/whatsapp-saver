package com.testing.firebase.analytic.click;

import com.testing.firebase.analytic.Analytics;

public abstract class AnalyticClickListener {
    protected final Analytics analytics;

    public AnalyticClickListener(Analytics analytics) {
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
