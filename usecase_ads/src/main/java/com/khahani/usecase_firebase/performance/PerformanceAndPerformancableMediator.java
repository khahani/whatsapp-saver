package com.khahani.usecase_firebase.performance;

public class PerformanceAndPerformancableMediator implements Runnable {
    private Performance performance;
    private Performancable performancable;


    private void runWithPerformanceMonitoring() {
        performance.newTrace(performancable.getTrackerKey());
        performance.setMustTraceObject(performancable);
        performance.run();
    }

    @Override
    public void run() {
        runWithPerformanceMonitoring();
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public void setPerformancable(Performancable performancable) {
        this.performancable = performancable;
    }
}
