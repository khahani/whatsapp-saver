package com.khahani.appodeal;

import android.app.Activity;

import com.appodeal.ads.BannerCallbacks;
import com.khahani.usecase_firebase.analytic.Analytics;
import com.khahani.usecase_firebase.analytic.click.TrackClick;

public class AppodealBannerCallback implements BannerCallbacks {
    private final Activity activity;
    private final Analytics analytic;

    public AppodealBannerCallback(Activity activity, Analytics analytic) {
        this.activity = activity;
        this.analytic = analytic;
    }

    @Override
    public void onBannerLoaded(int height, boolean isPrecache) {
        Utils.showToast(activity, String.format("onBannerLoaded, %sdp, isPrecache: %s", height, isPrecache));
    }

    @Override
    public void onBannerFailedToLoad() {
        Utils.showToast(activity, "onBannerFailedToLoad");
    }

    @Override
    public void onBannerShown() {
        runTrack(getShownId());
        Utils.showToast(activity, "onBannerShown");
    }

    @Override
    public void onBannerShowFailed() {
        Utils.showToast(activity, "onBannerShowFailed");
    }

    @Override
    public void onBannerClicked() {
        runTrack(getClickId());
        Utils.showToast(activity, "onBannerClicked");
    }

    @Override
    public void onBannerExpired() {
        Utils.showToast(activity, "onBannerExpired");
    }

    private void runTrack(String id) {
        TrackClick trackClick = new TrackClick(analytic, id, getName(), getType());
        trackClick.run();
    }

    private String getName() {
        return "Banner";
    }

    private String getClickId() {
        return activity.getString(R.string.click_on_ads);
    }

    private String getShownId() {
        return activity.getString(R.string.shown_an_ads);
    }

    private TrackClick.Type getType() {
        return TrackClick.Type.AppdealBanner;
    }

}
