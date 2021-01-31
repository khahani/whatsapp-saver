package com.testing.whatsapp.creator.firebase;

import android.content.Context;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.analytic.Analytics;
import com.khahani.usecase_firebase.analytic.NullAnalytics;
import com.testing.firebase.AnalyticsImpl;
import com.testing.whatsapp.BuildConfig;

public class AnalyticsCreator extends Creator<Analytics> {
    private final Context context;

    public AnalyticsCreator(Context context) {
        this.context = context;
    }

    @Override
    public Analytics factoryMethod() {
        if (!BuildConfig.INCLUDE_FIREBASE) {
            return new NullAnalytics(context);
        } else {
            return new AnalyticsImpl(context);
        }
    }
}
