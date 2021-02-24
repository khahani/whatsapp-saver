package com.whatsappear.creator.firebase;

import android.app.Activity;

import com.khahani.usecase_firebase.admob.Interstitial;
import com.khahani.usecase_firebase.admob.Nullnterstitial;
import com.khahani.usecase_firebase.analytic.Analytics;
import com.whatsappear.Activities.BaseActivity;

import java.lang.reflect.Constructor;

public class InterstitialCreator extends ReleaseModuleActivator<Interstitial> {
    private final Activity activity;
    private final String realInterstitialId;

    public InterstitialCreator(Activity activity, String realInterstitialId) {
        this.activity = activity;
        this.realInterstitialId = realInterstitialId;
    }

    @Override
    protected Interstitial getNullModule() {
        return new Nullnterstitial();
    }

    @Override
    protected Interstitial getReleaseModule() {
        return getInterstitial();
    }

    private Interstitial getInterstitial() {
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
