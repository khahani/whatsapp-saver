package com.whatsap.creator.firebase;

import android.content.Context;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.analytic.Analytics;
import com.testing.firebase.AnalyticsImpl;

public class AnalyticsCreator extends Creator<Analytics> {
    private final Context context;

    public AnalyticsCreator(Context context) {
        this.context = context;
    }

    @Override
    public Analytics factoryMethod() {
//        return new NullAnalytics(context);
        return new AnalyticsImpl(context);
    }
}
