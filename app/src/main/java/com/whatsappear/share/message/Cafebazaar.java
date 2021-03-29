package com.whatsappear.share.message;

import android.app.Activity;

import com.whatsappear.Activities.BaseActivityValidator;

public class Cafebazaar extends ShareTheApp {
    public Cafebazaar(Activity activity) {
        super(activity);
        new BaseActivityValidator(activity).run();
    }

    @Override
    protected String getMarketSiteUrl() {
        return "https://cafebazaar.ir/app/" + activity.getPackageName();
    }
}
