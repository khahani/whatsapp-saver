package com.whatsappear.share;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AppodealPrivacyPolicy implements Runnable {
    private final Context context;

    public AppodealPrivacyPolicy(Context context) {
        this.context = context;
    }

    public void showPrivacyPolicy() {
        String url = "https://appodeal.com/privacy-policy/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    @Override
    public void run() {
        showPrivacyPolicy();
    }
}