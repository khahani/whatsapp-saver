package com.testing.firebase.performance;

import com.google.firebase.perf.FirebasePerformance;
import com.khahani.usecase_firebase.performance.Trace;

public class TraceImpl extends Trace {

    private com.google.firebase.perf.metrics.Trace trace;

    @Override
    public void start() {
        trace.start();
    }

    @Override
    public void stop() {
        trace.stop();
    }


    @Override
    public void run() {
        trace = FirebasePerformance.getInstance().newTrace(key);
    }
}
