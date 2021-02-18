package com.khahani.appodeal;

import android.app.Activity;

import androidx.annotation.IdRes;

import com.appodeal.ads.Appodeal;
import com.khahani.usecase_firebase.OnCompletionListener;
import com.khahani.usecase_firebase.admob.Banner;
import com.khahani.usecase_firebase.analytic.Analytics;

public class AppodealBanner extends Banner implements OnCompletionListener {

    private final Activity activity;
    private final AppodealInitializer initializer;
    @IdRes
    private final int bannerId;
    private final Analytics analytic;

    public AppodealBanner(Activity activity, @IdRes int bannerId, Analytics analytic) {
        this.activity = activity;
        this.initializer = new AppodealInitializer(activity);
        this.bannerId = bannerId;
        this.analytic = analytic;
        initializer.setCompletionListener(this);
    }

    @Override
    public void run() {
        initializer.run();
    }

    private void getBannerView() {
        Appodeal.getBannerView(activity);
    }

    @Override
    public void onCompleted(Boolean completed) {
        if (completed) {
            Appodeal.set728x90Banners(true);
            Appodeal.setBannerAnimation(true);
            Appodeal.setSmartBanners(true);
            Appodeal.setBannerViewId(bannerId);
            Appodeal.setBannerCallbacks(new AppodealBannerCallback(activity, analytic));
            Appodeal.show(activity, Appodeal.BANNER);
        } else {
            initializer.showUpdateConsentForm();
        }
    }


}
