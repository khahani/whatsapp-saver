package com.khahani.appodeal;

import android.app.Activity;

import com.appodeal.ads.InterstitialCallbacks;
import com.khahani.usecase_firebase.analytic.Analytics;
import com.khahani.usecase_firebase.analytic.LogEvent;
import com.khahani.usecase_firebase.analytic.click.TrackClick;

public class AppodealInterstitialCallback implements InterstitialCallbacks {
    private final Activity activity;
    private final Analytics analytic;

    private LogEvent logEvent;
    private TrackClick trackClick;

    public AppodealInterstitialCallback(Activity activity, Analytics analytic) {
        this.activity = activity;
        this.analytic = analytic;
    }

    @Override
    public void onInterstitialLoaded(boolean isPrecache) {
        Utils.showToast(activity, String.format("onInterstitialLoaded, isPrecache: %s", isPrecache));
    }

    @Override
    public void onInterstitialFailedToLoad() {
        Utils.showToast(activity, "onInterstitialFailedToLoad");
    }

    @Override
    public void onInterstitialShown() {
        trackShown();
        Utils.showToast(activity, "onInterstitialShown");
    }

    private void trackShown() {
        runTrack(getShownId());
    }

    @Override
    public void onInterstitialShowFailed() {
        Utils.showToast(activity, "onInterstitialShowFailed");
    }

    @Override
    public void onInterstitialClicked() {
        trackClick();
        Utils.showToast(activity, "onInterstitialClicked");
    }

    private void trackClick() {
        runTrack(getClickId());
    }

    @Override
    public void onInterstitialClosed() {
        Utils.showToast(activity, "onInterstitialClosed");
    }

    @Override
    public void onInterstitialExpired() {
        Utils.showToast(activity, "onInterstitialExpired");
    }

    private void runTrack(String id) {
        TrackClick trackClick = new TrackClick(analytic, id, getName(), getType());
        trackClick.run();
    }

    private String getName() {
        return "Interstitial";
    }

    private String getClickId() {
        return activity.getString(R.string.click_on_ads);
    }

    private String getShownId() {
        return activity.getString(R.string.shown_an_ads);
    }

    private TrackClick.Type getType() {
        return TrackClick.Type.AppodealInterstitial;
    }

}
