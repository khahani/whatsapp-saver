package com.testing.whatsapp.creator.firebase;

import android.app.Activity;
import android.view.View;

import androidx.annotation.StringRes;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.admob.Banner;
import com.testing.firebase.admob.AdapterBannerImpl;

public class AdapterBannerCreator extends Creator<Banner> {

    private final Activity context;
    private final View layout;
    @StringRes
    private final int bannerContainerId;
    private final String realBannerId;

    public AdapterBannerCreator(Activity activity, View layout, int bannerContainerId, String realBannerId) {
        this.context = activity;
        this.layout = layout;
        this.bannerContainerId = bannerContainerId;
        this.realBannerId = realBannerId;
    }

    @Override
    public Banner factoryMethod() {
//        return new NullAdapterBanner();
        return new AdapterBannerImpl(context, layout, bannerContainerId, realBannerId);
    }
}
