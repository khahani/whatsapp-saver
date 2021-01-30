package com.khahani.usecase_firebase;

public abstract class InAppMessageBase implements Runnable {
    protected final Runnable listener;
    protected String uniqueId;

    public InAppMessageBase(Runnable listener) {
        this.listener = listener;
    }

    public String getDeviceId() {
        return uniqueId;
    }
}
