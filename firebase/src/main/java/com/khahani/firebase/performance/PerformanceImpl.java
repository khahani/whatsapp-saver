package com.khahani.firebase.performance;


import com.khahani.usecase_firebase.performance.Performance;
import com.khahani.usecase_firebase.performance.Trace;

public class PerformanceImpl extends Performance {

    @Override
    public Trace newTrace(String key) {
        Trace t = new TraceImpl();
        t.setKey(key);
        t.run();
        return t;
    }

}
