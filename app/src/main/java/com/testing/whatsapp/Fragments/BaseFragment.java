package com.testing.whatsapp.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.khahani.usecase_firebase.analytic.Analytics;
import com.khahani.usecase_firebase.analytic.LogEvent;
import com.khahani.usecase_firebase.analytic.screen.TrackScreen;
import com.khahani.usecase_firebase.analytic.screen.TrackableScreen;
import com.testing.whatsapp.creator.firebase.AnalyticsCreator;

public abstract class BaseFragment extends Fragment implements TrackableScreen {
    private TrackScreen trackScreen;
    private Analytics analytic;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analytic = new AnalyticsCreator(getContext()).factoryMethod();
        trackScreen = initTrackScreen(analytic);
    }

    protected abstract TrackScreen initTrackScreen(LogEvent logger);
}
