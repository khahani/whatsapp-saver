package com.khahani.appodeal;

import android.app.Activity;

import com.appodeal.ads.Appodeal;
import com.khahani.usecase_firebase.admob.Banner;

public class AppodealBanner extends Banner {

    private final Activity activity;
    private final AppodealInitializer initializer;

    public AppodealBanner(Activity activity) {
        this.activity = activity;
        this.initializer = new AppodealInitializer(activity);
    }

    @Override
    public void run() {
        initializer.run();
        Appodeal.show(activity, Appodeal.BANNER_BOTTOM);
    }

    private void getBannerView() {
        Appodeal.getBannerView(activity);
    }
}
