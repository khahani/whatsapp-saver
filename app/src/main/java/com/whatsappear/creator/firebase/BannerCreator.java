package com.whatsappear.creator.firebase;

import android.app.Activity;
import android.view.View;

import androidx.annotation.IdRes;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.admob.Banner;
import com.khahani.usecase_firebase.admob.NullAdapterBanner;

public class BannerCreator extends Creator<Banner> {

    private final Activity activity;
    private final View layout;
    @IdRes
    private final int bannerContainerId;
    private final String realBannerId;

    public BannerCreator(Activity activity, View layout, int bannerContainerId, String realBannerId) {
        this.activity = activity;
        this.layout = layout;
        this.bannerContainerId = bannerContainerId;
        this.realBannerId = realBannerId;
    }

    @Override
    public Banner factoryMethod() {
        return new NullAdapterBanner();
        //return new AdapterBannerImpl(activity, layout, bannerContainerId, realBannerId);
        //return new AppodealBanner(activity, bannerContainerId, ((BaseActivity) activity).getAnalytic());
    }
}
