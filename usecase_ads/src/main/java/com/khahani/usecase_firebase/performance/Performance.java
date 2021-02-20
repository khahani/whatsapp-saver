package com.khahani.usecase_firebase.performance;

public abstract class Performance implements Runnable {

    protected Trace trace;
    private Runnable mustTraceObject;

    public abstract void newTrace(String key);

    @Override
    public void run() {
        if (mustTraceObject == null)
            throw new MustTraceObjectNotSetException();

        trace.start();
        mustTraceObject.run();
        trace.stop();
    }

    public void setMustTraceObject(Runnable mustTraceObject) {
        this.mustTraceObject = mustTraceObject;
    }

    public static class MustTraceObjectNotSetException extends RuntimeException {
    }
}
