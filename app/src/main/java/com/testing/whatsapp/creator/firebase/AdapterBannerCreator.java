package com.testing.whatsapp.creator.firebase;

import android.content.Context;
import android.view.View;

import androidx.annotation.StringRes;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.admob.AdapterBanner;
import com.khahani.usecase_firebase.admob.NullAdapterBanner;

public class AdapterBannerCreator extends Creator<AdapterBanner> {

    private final Context context;
    private final View layout;
    @StringRes
    private final int bannerContainerId;
    private final String realBannerId;

    public AdapterBannerCreator(Context context, View layout, int bannerContainerId, String realBannerId) {
        this.context = context;
        this.layout = layout;
        this.bannerContainerId = bannerContainerId;
        this.realBannerId = realBannerId;
    }

    @Override
    public AdapterBanner factoryMethod() {
        return new NullAdapterBanner();
        // new AdapterBanner(context, layout, bannerContainerId, realBannerId);
    }
}
