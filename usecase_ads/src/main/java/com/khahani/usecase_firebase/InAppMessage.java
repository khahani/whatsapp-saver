package com.khahani.usecase_firebase;

import android.content.Context;

public abstract class InAppMessage implements Runnable {
    protected Context context;
    protected String uniqueId;

    public InAppMessage(Context context) {
        this.context = context;
    }
}
