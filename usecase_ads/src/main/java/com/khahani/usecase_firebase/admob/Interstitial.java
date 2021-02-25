package com.khahani.usecase_firebase.admob;

public abstract class Interstitial implements Runnable {
    protected abstract void loadAd();

    protected abstract void show();

    public interface InterstitialInteraction {
        void onInterstitialReady();
    }
}
