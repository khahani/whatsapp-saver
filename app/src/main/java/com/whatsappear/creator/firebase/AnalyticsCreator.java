package com.whatsappear.creator.firebase;

import android.content.Context;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.analytic.Analytics;
import com.khahani.usecase_firebase.analytic.NullAnalytics;
import com.whatsappear.BuildConfig;

import java.lang.reflect.Constructor;

public class AnalyticsCreator extends Creator<Analytics> {
    private final Context context;

    public AnalyticsCreator(Context context) {
        this.context = context;
    }

    @Override
    public Analytics factoryMethod() {
        if (BuildConfig.DEBUG) {
            return new NullAnalytics(context);
        }

        return getAnalytics();
    }

    private Analytics getAnalytics() {
        try {
            Constructor<?> c = Class.forName("com.khahani.firebase.AnalyticsImpl").getConstructor(Context.class);
            return (Analytics) c.newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
            return new NullAnalytics(context);
        }
    }
}
