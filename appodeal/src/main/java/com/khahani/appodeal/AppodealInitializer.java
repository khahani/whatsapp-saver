package com.khahani.appodeal;

import android.app.Activity;

import com.appodeal.ads.Appodeal;
import com.explorestack.consent.Consent;
import com.explorestack.consent.ConsentManager;

public class AppodealInitializer implements Runnable {
    private final Activity activity;

    public AppodealInitializer(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void run() {
        try {
            Consent consent = ConsentManager.getInstance(activity).getConsent();
            if (consent != null) {
                int adTypes = Appodeal.INTERSTITIAL | Appodeal.BANNER;
                Appodeal.initialize(activity, activity.getString(R.string.appodeal_key), adTypes, consent);
                Appodeal.setTesting(BuildConfig.DEBUG);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
