package com.whatsappear.Activities;

import android.app.Activity;

import androidx.annotation.Nullable;

public class BaseActivityValidator implements Runnable {

    private final Activity activity;

    public BaseActivityValidator(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        check();
    }

    private void check() {
        if (!(activity instanceof BaseActivity))
            throw new NotValidInstance();
    }

    public static class NotValidInstance extends RuntimeException {
        @Nullable
        @Override
        public String getMessage() {
            return String.format("Activity must be an instance of %s type", BaseActivity.class.getName());
        }
    }
}
