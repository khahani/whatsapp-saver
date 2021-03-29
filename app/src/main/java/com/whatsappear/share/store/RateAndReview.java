package com.whatsappear.share.store;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public abstract class RateAndReview implements Runnable {
    protected final Context context;

    public RateAndReview(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        rateAndReview();
    }

    private void rateAndReview() {
        Uri uri = getMarketIntentUri();
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(getFlags());
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    getMarketSiteUri()));
        }
    }

    protected abstract Uri getMarketIntentUri();

    protected abstract Uri getMarketSiteUri();

    private int getFlags() {
        int flags;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        } else {
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        }
        return flags;
    }
}
