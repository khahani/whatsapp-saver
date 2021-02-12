package com.khahani.appodeal;

import android.app.Activity;

import com.appodeal.ads.Appodeal;
import com.explorestack.consent.Consent;
import com.explorestack.consent.ConsentManager;
import com.khahani.usecase_firebase.admob.Banner;

public class AppodealBanner extends Banner {

    private final Activity activity;

    public AppodealBanner(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {

        Consent consent = ConsentManager.getInstance(activity).getConsent();
        int adTypes = Appodeal.INTERSTITIAL | Appodeal.BANNER;
        Appodeal.initialize(activity, activity.getString(R.string.appodeal_key), adTypes, consent);
        Appodeal.setTesting(BuildConfig.DEBUG);
        Appodeal.show(activity, Appodeal.BANNER_BOTTOM);
    }
}
