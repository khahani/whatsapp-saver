package com.whatsappear.creator.firebase;

import android.app.Activity;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.admob.Interstitial;
import com.khahani.usecase_firebase.admob.Nullnterstitial;
import com.khahani.usecase_firebase.analytic.Analytics;
import com.whatsappear.Activities.BaseActivity;
import com.whatsappear.BuildConfig;

import java.lang.reflect.Constructor;

public class InterstitialCreator extends Creator<Interstitial> {
    private final Activity activity;
    private final String realInterstitialId;

    public InterstitialCreator(Activity activity, String realInterstitialId) {
        this.activity = activity;
        this.realInterstitialId = realInterstitialId;
    }

    @Override
    public Interstitial factoryMethod() {
        if (BuildConfig.DEBUG) {
            return new Nullnterstitial();
        }
        try {
//        return new InterstitialImpl(activity, realInterstitialId);
//            return new AppodealInterstitial(activity, ((BaseActivity) activity).getAnalytic());
            Constructor<?> c = Class.forName("com.khahani.appodeal.AppodealInterstitial")
                    .getConstructor(Activity.class, Analytics.class);
            return (Interstitial) c.newInstance(activity, ((BaseActivity) activity).getAnalytic());
        } catch (Exception e) {
            e.printStackTrace();
            return new Nullnterstitial();
        }
    }
}
