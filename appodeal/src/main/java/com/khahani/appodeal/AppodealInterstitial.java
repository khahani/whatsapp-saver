package com.khahani.appodeal;

import android.app.Activity;

import com.appodeal.ads.Appodeal;
import com.khahani.usecase_firebase.admob.Interstitial;

public class AppodealInterstitial extends Interstitial {

    private final Activity activity;

    public AppodealInterstitial(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void loadAd() {

    }

    @Override
    protected void show() {
        Appodeal.show(activity, Appodeal.INTERSTITIAL);
    }

    @Override
    public void run() {
        show();
    }
}
