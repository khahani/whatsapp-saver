package com.khahani.usecase_firebase;

public abstract class InAppMessage implements Runnable {
    protected final Runnable listener;
    protected String uniqueId;

    public InAppMessage(Runnable listener) {
        this.listener = listener;
    }

    public String getDeviceId() {
        return uniqueId;
    }
}
