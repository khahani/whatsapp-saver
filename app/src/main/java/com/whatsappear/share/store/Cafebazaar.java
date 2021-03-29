package com.whatsappear.share.store;

import android.content.Context;
import android.net.Uri;

public class Cafebazaar extends RateAndReview {
    public Cafebazaar(Context context) {
        super(context);
    }

    @Override
    protected Uri getMarketIntentUri() {
        return Uri.parse(String.format("bazaar://details?id=%s", context.getPackageName()));
    }

    @Override
    protected Uri getMarketSiteUri() {
        return Uri.parse(String.format("https://cafebazaar.ir/app/%s", context.getPackageName()));
    }
}
