package com.testing.whatsapp.admob;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.testing.whatsapp.BuildConfig;
import com.testing.whatsapp.R;

public class Interstitial {

    private final Activity activity;
    private final InterstitialInteraction listener;
    private InterstitialAd mInterstitialAd;

    public Interstitial(Activity activity, InterstitialInteraction listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        String adUid = "";

        if (BuildConfig.DEBUG) {
            adUid = activity.getString(R.string.interstitial_test_uid);
        } else {
            adUid = "!"; //khahani: replace with real value from admob
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

    public void show() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

    public interface InterstitialInteraction {
        void onInterstitialReady();
    }
}
