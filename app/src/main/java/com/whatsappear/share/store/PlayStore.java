package com.whatsappear.share.store;

import android.content.Context;
import android.net.Uri;

public class PlayStore extends RateAndReview {

    public PlayStore(Context context) {
        super(context);
    }

    @Override
    protected Uri getMarketIntentUri() {
        return Uri.parse(String.format("market://details?id=%s", context.getPackageName()));
    }

    @Override
    protected Uri getMarketSiteUri() {
        return Uri.parse(String.format("http://play.google.com/store/apps/details?id=%s", context.getPackageName()));
    }

}
