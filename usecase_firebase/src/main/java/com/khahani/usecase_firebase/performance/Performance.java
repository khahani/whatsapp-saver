package com.khahani.usecase_firebase.performance;

public abstract class Performance implements Runnable {
    public abstract Track newTrack(String key);
}
