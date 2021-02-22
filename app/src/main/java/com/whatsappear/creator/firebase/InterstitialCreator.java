package com.whatsappear.creator.firebase;

import android.app.Activity;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.admob.Interstitial;
import com.khahani.usecase_firebase.admob.Nullnterstitial;

public class InterstitialCreator extends Creator<Interstitial> {
    private final Activity activity;
    private final String realInterstitialId;

    public InterstitialCreator(Activity activity, String realInterstitialId) {
        this.activity = activity;
        this.realInterstitialId = realInterstitialId;
    }

    @Override
    public Interstitial factoryMethod() {
       return new Nullnterstitial();
//        return new InterstitialImpl(activity, realInterstitialId);
        //return new AppodealInterstitial(activity, ((BaseActivity) activity).getAnalytic());
    }
}
