package com.khahani.appodeal;

import android.app.Activity;

import com.appodeal.ads.Appodeal;
import com.khahani.usecase_firebase.OnCompletionListener;
import com.khahani.usecase_firebase.admob.Interstitial;
import com.khahani.usecase_firebase.analytic.Analytics;

public class AppodealInterstitial extends Interstitial implements OnCompletionListener {

    private final Activity activity;
    private final AppodealInitializer initializer;
    private final Analytics analytic;

    public AppodealInterstitial(Activity activity, Analytics analytic) {
        this.activity = activity;
        this.initializer = new AppodealInitializer(activity);
        this.analytic = analytic;
        initializer.setCompletionListener(this);
    }

    @Override
    protected void loadAd() {

    }

    @Override
    protected void show() {
        Appodeal.setInterstitialCallbacks(new AppodealInterstitialCallback(activity, analytic));
        Appodeal.show(activity, Appodeal.INTERSTITIAL);
    }

    @Override
    public void run() {
        initializer.run();
    }

    @Override
    public void onCompleted(Boolean completed) {
        Appodeal.initialize(activity, initializer.getAppodealAppKey(), Appodeal.INTERSTITIAL, completed);
        show();

        if (!completed) {
            initializer.showUpdateConsentForm();
        }
    }
}
