package com.whatsappear;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class RateAndReview implements Runnable {

    private final Context context;

    public RateAndReview(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        rateAndReview();
    }

    private void rateAndReview() {
        Uri uri = Uri.parse(String.format("market://details?id=%s", context.getPackageName()));
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(getFlags());
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(String.format("http://play.google.com/store/apps/details?id=%s", context.getPackageName()))));
        }
    }

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
