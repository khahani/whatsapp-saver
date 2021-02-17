package com.khahani.appodeal;

import android.app.Activity;

import androidx.annotation.IdRes;

import com.appodeal.ads.Appodeal;
import com.khahani.usecase_firebase.OnCompletionListener;
import com.khahani.usecase_firebase.admob.Banner;

public class AppodealBanner extends Banner implements OnCompletionListener {

    private final Activity activity;
    private final AppodealInitializer initializer;
    @IdRes
    private final int bannerId;

    public AppodealBanner(Activity activity, @IdRes int bannerId) {
        this.activity = activity;
        this.initializer = new AppodealInitializer(activity);
        this.bannerId = bannerId;
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
            Appodeal.setBannerViewId(bannerId);
            Appodeal.setBannerCallbacks(new AppodealBannerCallback(activity));
            Appodeal.show(activity, Appodeal.BANNER);
        } else {
            initializer.showUpdateConsentForm();
        }
    }


}
