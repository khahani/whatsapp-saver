package com.whatsappear.share.message;

import android.app.Activity;

import com.whatsappear.Activities.BaseActivityValidator;

public class PlayStore extends ShareTheApp {

    public PlayStore(Activity activity) {
        super(activity);
        new BaseActivityValidator(activity).run();
    }

    @Override
    protected String getMarketSiteUrl() {
        return "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
    }

}