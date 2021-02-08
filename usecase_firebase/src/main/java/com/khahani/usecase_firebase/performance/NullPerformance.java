package com.khahani.usecase_firebase.performance;

public class NullPerformance extends Performance {
    @Override
    public void run() {

    }

    @Override
    public Track newTrack(String key) {
        return new NullTrack();
    }
}
