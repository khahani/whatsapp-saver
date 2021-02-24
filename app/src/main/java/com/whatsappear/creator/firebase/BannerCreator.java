package com.whatsappear.creator.firebase;

import android.app.Activity;
import android.view.View;

import androidx.annotation.IdRes;

import com.khahani.usecase_firebase.admob.Banner;
import com.khahani.usecase_firebase.admob.NullAdapterBanner;
import com.khahani.usecase_firebase.analytic.Analytics;
import com.whatsappear.Activities.BaseActivity;

import java.lang.reflect.Constructor;

public class BannerCreator extends ReleaseModuleActivator<Banner> {

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
    protected Banner getNullModule() {
        return new NullAdapterBanner();
    }

    @Override
    protected Banner getReleaseModule() {
        return getBanner();
    }

    private Banner getBanner() {
        try {
            Constructor<?> c = Class.forName("com.khahani.appodeal.AppodealBanner")
                    .getConstructor(Activity.class, int.class, Analytics.class);
            return (Banner) c.newInstance(activity, bannerContainerId, ((BaseActivity) activity).getAnalytic());
        } catch (Exception e) {
            e.printStackTrace();
            return new NullAdapterBanner();
        }
        //khahani: think about a way to switch between admob and appodeal
        //return new AdapterBannerImpl(activity, layout, bannerContainerId, realBannerId);
        //return new AppodealBanner(activity, bannerContainerId, ((BaseActivity) activity).getAnalytic());
    }
}
