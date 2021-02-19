package com.khahani.firebase.admob;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.khahani.firebase.BuildConfig;
import com.khahani.firebase.R;
import com.khahani.usecase_firebase.admob.Banner;

import java.util.Objects;

public class AdapterBannerImpl extends Banner {
    private final Activity activity;
    private final View layout;
    private AdView adView;
    private final int frameLayoutId;
    private final String firebaseBannerUID;

    public AdapterBannerImpl(Activity activity, View layout, int frameLayoutId, String firebaseBannerUID) {
        this.activity = activity;
        this.layout = layout;
        this.frameLayoutId = frameLayoutId;
        this.firebaseBannerUID = firebaseBannerUID;
    }

    public void initAds() {
        MobileAds.initialize(activity, initializationStatus -> {
        });

        FrameLayout bannerContainer = layout.findViewById(frameLayoutId);
        adView = new AdView(Objects.requireNonNull(activity));
        if (BuildConfig.DEBUG) {
            adView.setAdUnitId(activity.getString(R.string.banner_test_uid));
        } else {
            adView.setAdUnitId(firebaseBannerUID);
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

    @Override
    public void run() {
        initAds();
    }
}
