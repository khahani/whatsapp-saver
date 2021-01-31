package com.testing.whatsapp.creator.firebase;

import android.app.Activity;

import androidx.annotation.StringRes;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.admob.Interstitial;
import com.khahani.usecase_firebase.admob.Nullnterstitial;

public class InterstitialCreator extends Creator<Interstitial> {
    private Activity activity;
    @StringRes
    private int realInterstitialId;

    @Override
    public Interstitial factoryMethod() {
        return new Nullnterstitial();
        //khahani: should test in release
        //return new Interstitial(getActivity(), new InterstitialListenerImpl(), realInterstitialId);
    }

    private void init() {
        //Interstitial i = new InterstitialImpl(activity, new InterstitialListenerImpl(this), realInterstitialId);
    }
}
