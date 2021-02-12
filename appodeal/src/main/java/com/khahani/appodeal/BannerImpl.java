package com.khahani.appodeal;

import android.content.Context;

import com.explorestack.consent.Consent;
import com.explorestack.consent.ConsentManager;
import com.khahani.usecase_firebase.admob.Banner;

public class BannerImpl extends Banner {

    private Context context;

    @Override
    public void run() {

        Consent consent = ConsentManager.getInstance(context).getConsent();


    }
}
