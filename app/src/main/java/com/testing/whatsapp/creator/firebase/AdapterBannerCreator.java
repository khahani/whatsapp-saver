package com.testing.whatsapp.creator.firebase;

import android.app.Activity;
import android.view.View;

import androidx.annotation.StringRes;

import com.khahani.appodeal.AppodealBanner;
import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.admob.Banner;

public class AdapterBannerCreator extends Creator<Banner> {

    private final Activity activity;
    private final View layout;
    @StringRes
    private final int bannerContainerId;
    private final String realBannerId;

    public AdapterBannerCreator(Activity activity, View layout, int bannerContainerId, String realBannerId) {
        this.activity = activity;
        this.layout = layout;
        this.bannerContainerId = bannerContainerId;
        this.realBannerId = realBannerId;
    }

    @Override
    public Banner factoryMethod() {
//        return new NullAdapterBanner();
//        return new AdapterBannerImpl(activity, layout, bannerContainerId, realBannerId);
        return new AppodealBanner(activity);
    }
}
