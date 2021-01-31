package com.testing.whatsapp.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.khahani.usecase_firebase.analytic.AnalyticsBase;
import com.khahani.usecase_firebase.analytic.LogEvent;
import com.khahani.usecase_firebase.analytic.screen.TrackScreen;
import com.khahani.usecase_firebase.analytic.screen.TrackableScreen;

public abstract class BaseFragment extends Fragment implements TrackableScreen {
    private TrackScreen trackScreen;
    private AnalyticsBase analytic;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analytic = new Analytics(getContext());
        trackScreen = initTrackScreen(analytic);
    }

    protected abstract TrackScreen initTrackScreen(LogEvent logger);
}
