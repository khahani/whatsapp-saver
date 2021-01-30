package com.testing.firebase.admob;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.khahani.usecase_firebase.admob.InterstitialBase;
import com.testing.firebase.BuildConfig;
import com.testing.firebase.R;

public class Interstitial extends InterstitialBase {

    private final Activity activity;
    private final InterstitialInteraction listener;
    private InterstitialAd mInterstitialAd;
    private String adUid;

    public Interstitial(Activity activity, InterstitialInteraction listener, String adUid) {
        this.activity = activity;
        this.listener = listener;
        this.adUid = adUid;
    }

    @Override
    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        if (BuildConfig.DEBUG) {
            adUid = activity.getString(R.string.interstitial_test_uid);
        }

        InterstitialAd.load(activity, adUid, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                setCallback();
                listener.onInterstitialReady();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
            }
        });
    }

    private void setCallback() {
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
            }

            @Override
            public void onAdShowedFullScreenContent() {
                mInterstitialAd = null;
            }
        });
    }

    @Override
    public void show() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

}
