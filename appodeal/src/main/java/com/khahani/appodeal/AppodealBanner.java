package com.khahani.appodeal;

import android.app.Activity;

import com.appodeal.ads.Appodeal;
import com.khahani.usecase_firebase.OnCompletionListener;
import com.khahani.usecase_firebase.admob.Banner;

public class AppodealBanner extends Banner implements OnCompletionListener {

    private final Activity activity;
    private final AppodealInitializer initializer;

    public AppodealBanner(Activity activity) {
        this.activity = activity;
        this.initializer = new AppodealInitializer(activity);
        initializer.setCompletionListener(this);
    }

    private void show() {
        Appodeal.show(activity, Appodeal.BANNER_BOTTOM);
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
        if (completed)
            show();
        else {
            initializer.showUpdateConsentForm();
        }
    }


}
