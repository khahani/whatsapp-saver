package com.khahani.firebase.performance;


import com.khahani.usecase_firebase.performance.Performance;

public class PerformanceImpl extends Performance {

    @Override
    public void newTrace(String key) {
        trace = new TraceImpl();
        trace.setKey(key);
        trace.run();
    }

}
