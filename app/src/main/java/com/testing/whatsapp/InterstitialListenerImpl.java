package com.testing.whatsapp;

import com.khahani.usecase_firebase.admob.Interstitial;

public class InterstitialListenerImpl implements Runnable {

    private final Interstitial interstitial;

    public InterstitialListenerImpl(Interstitial interstitial) {
        this.interstitial = interstitial;
    }

    @Override
    public void run() {
        interstitial.show();
    }
}
