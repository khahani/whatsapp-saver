package com.khahani.usecase_firebase.performance;

public abstract class Performance implements Runnable {
    public abstract Trace newTrace(String key);

    @Override
    public void run() {

    }
}
