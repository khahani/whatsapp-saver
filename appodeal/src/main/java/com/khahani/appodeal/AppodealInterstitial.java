package com.khahani.appodeal;

import android.app.Activity;

import com.appodeal.ads.Appodeal;
import com.khahani.usecase_firebase.OnCompletionListener;
import com.khahani.usecase_firebase.admob.Interstitial;

public class AppodealInterstitial extends Interstitial implements OnCompletionListener {

    private final Activity activity;
    private final AppodealInitializer initializer;

    public AppodealInterstitial(Activity activity) {
        this.activity = activity;
        this.initializer = new AppodealInitializer(activity);
        initializer.setCompletionListener(this);
    }

    @Override
    protected void loadAd() {

    }

    @Override
    protected void show() {
        Appodeal.setInterstitialCallbacks(new AppodealInterstitialCallback(activity));
        Appodeal.show(activity, Appodeal.INTERSTITIAL);
    }

    @Override
    public void run() {
        initializer.run();
    }

    @Override
    public void onCompleted(Boolean completed) {
        if (completed)
            show();
    }
}
