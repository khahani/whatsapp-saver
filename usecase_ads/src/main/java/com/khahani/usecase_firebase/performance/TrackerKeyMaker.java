package com.khahani.usecase_firebase.performance;

public class TrackerKeyMaker implements Runnable {
    private Object object;
    private String methodName;
    private String key;

    public static TrackerKeyMaker getInstance(Object object, String methodName) {
        TrackerKeyMaker t = new TrackerKeyMaker();
        t.object = object;
        t.methodName = methodName;
        return t;
    }

    public void make() {
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append(object.getClass().getName());
        sb.append(".");
        sb.append(methodName);
        sb.append("()");
        key = sb.toString();
    }

    @Override
    public void run() {
        make();
    }

    public String getKey() {
        return key;
    }
}
