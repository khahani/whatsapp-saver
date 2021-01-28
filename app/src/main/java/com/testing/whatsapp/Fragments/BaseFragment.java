package com.testing.whatsapp.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.testing.firebase.analytic.Analytics;
import com.testing.firebase.analytic.LogEvent;
import com.testing.firebase.analytic.screen.TrackScreen;
import com.testing.firebase.analytic.screen.TrackableScreen;

public abstract class BaseFragment extends Fragment implements TrackableScreen {
    protected TrackScreen trackScreen;
    private Analytics analytic;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analytic = new Analytics(getContext());
        trackScreen = initTrackScreen(analytic);
    }

    protected abstract TrackScreen initTrackScreen(LogEvent logger);
}
