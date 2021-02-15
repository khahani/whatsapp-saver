package com.khahani.appodeal;

import android.app.Activity;

import com.appodeal.ads.Appodeal;
import com.khahani.usecase_firebase.admob.Banner;

public class AppodealBanner extends Banner {

    private final Activity activity;

    public AppodealBanner(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        Appodeal.show(activity, Appodeal.BANNER_BOTTOM);
    }

    private void getBannerView() {
        Appodeal.getBannerView(activity);
    }
}
