package com.khahani.whatsapp.firebase.analytic;

import android.os.Bundle;

public interface LogEvent {
    void logEvent(String key, Bundle value);
}