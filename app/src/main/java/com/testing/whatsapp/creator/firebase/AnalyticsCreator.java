package com.testing.whatsapp.creator.firebase;

import android.content.Context;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.analytic.Analytics;
import com.khahani.usecase_firebase.analytic.NullAnalytics;

public class AnalyticsCreator extends Creator<Analytics> {
    private final Context context;

    public AnalyticsCreator(Context context) {
        this.context = context;
    }

    @Override
    public Analytics factoryMethod() {
        return new NullAnalytics(context);
    }
}
