package com.khahani.usecase_firebase.performance;

public class NullPerformance extends Performance {

    @Override
    public void newTrace(String key) {
        trace = new NullTrace();
    }
}
