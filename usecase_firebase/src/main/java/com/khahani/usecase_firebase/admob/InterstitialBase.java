package com.khahani.usecase_firebase.admob;

public abstract class InterstitialBase {
    public abstract void loadAd();

    public abstract void show();

    public interface InterstitialInteraction {
        void onInterstitialReady();
    }
}
