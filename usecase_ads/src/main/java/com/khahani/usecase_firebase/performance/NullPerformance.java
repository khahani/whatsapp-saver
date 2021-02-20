package com.khahani.usecase_firebase.performance;

public class NullPerformance extends Performance {

    @Override
    public Trace newTrace(String key) {
        return new NullTrace();
    }
}
