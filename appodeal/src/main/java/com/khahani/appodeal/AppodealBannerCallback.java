package com.khahani.appodeal;

import android.app.Activity;

import com.appodeal.ads.BannerCallbacks;

public class AppodealBannerCallback implements BannerCallbacks {
    private final Activity activity;

    public AppodealBannerCallback(Activity activity) {
        this.activity = activity;
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
        Utils.showToast(activity, "onBannerShown");
    }

    @Override
    public void onBannerShowFailed() {
        Utils.showToast(activity, "onBannerShowFailed");
    }

    @Override
    public void onBannerClicked() {
        //release: Dont forget to log use clicks for analytic
        Utils.showToast(activity, "onBannerClicked");
    }

    @Override
    public void onBannerExpired() {
        Utils.showToast(activity, "onBannerExpired");
    }

}
