package com.khahani.usecase_firebase.analytic;

import android.content.Context;
import android.os.Bundle;

public class NullAnalytics extends Analytics {
    public NullAnalytics(Context context) {
        super(context);
    }

    @Override
    public void logEvent(String key, Bundle value) {

    }

    @Override
    public void run() {

    }
}
