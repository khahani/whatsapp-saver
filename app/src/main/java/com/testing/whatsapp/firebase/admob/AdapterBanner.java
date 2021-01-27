package com.testing.whatsapp.firebase.admob;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.testing.whatsapp.BuildConfig;
import com.testing.whatsapp.R;

import java.util.Objects;

public class AdapterBanner {
    private final Activity activity;
    private final View layout;
    private AdView adView;

    public AdapterBanner(Activity activity, View layout) {
        this.activity = activity;
        this.layout = layout;
    }

    public void initAds() {
        MobileAds.initialize(activity, initializationStatus -> {
        });

        FrameLayout bannerContainer = layout.findViewById(R.id.bannerContainer);
        adView = new AdView(Objects.requireNonNull(activity));
        if (BuildConfig.DEBUG) {
            adView.setAdUnitId(activity.getString(R.string.banner_test_uid));
        } else {
            adView.setAdUnitId(""); //khahani: place original banner ads uid
        }
        bannerContainer.addView(adView);
        loadBanner();
    }

    private void loadBanner() {
        AdRequest.Builder adRequestBuilder =
                new AdRequest.Builder();

        if (BuildConfig.DEBUG) {
            // Create an ad request. Check your logcat output for the hashed device ID
            // to get test ads on a physical device, e.g.,
            // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
            // device."
            adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        }

        AdRequest adRequest = adRequestBuilder.build();

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        Display display = Objects.requireNonNull(activity).getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

}
