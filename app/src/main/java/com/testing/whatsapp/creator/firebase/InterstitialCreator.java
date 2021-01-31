package com.testing.whatsapp.creator.firebase;

import android.app.Activity;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.admob.Interstitial;
import com.testing.firebase.admob.InterstitialImpl;

public class InterstitialCreator extends Creator<Interstitial> {
    private final Activity activity;
    private final String realInterstitialId;
    private Interstitial interstitial;

    public InterstitialCreator(Activity activity, String realInterstitialId, Interstitial interstitial) {
        this.activity = activity;
        this.realInterstitialId = realInterstitialId;
        this.interstitial = interstitial;
    }

    @Override
    public Interstitial factoryMethod() {
//        return new Nullnterstitial();
        interstitial = new InterstitialImpl(activity, () -> interstitial.show(), realInterstitialId);
        return interstitial;
    }
}
