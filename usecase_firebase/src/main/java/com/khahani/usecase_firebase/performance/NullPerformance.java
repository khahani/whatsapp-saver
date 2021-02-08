package com.khahani.usecase_firebase.performance;

public class NullPerformance extends Performance {
    @Override
    public void run() {

    }

    @Override
    public Trace newTrace(String key) {
        return new NullTrace();
    }
}
