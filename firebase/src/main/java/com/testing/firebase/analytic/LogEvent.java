package com.testing.firebase.analytic;

import android.os.Bundle;

public interface LogEvent {
    void logEvent(String key, Bundle value);
}