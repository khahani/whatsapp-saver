package com.khahani.usecase_firebase.performance;

public abstract class Trace implements Runnable {
    protected String key;

    public void setKey(String key) {
        this.key = key;
    }

    public abstract void start();

    public abstract void stop();
}
