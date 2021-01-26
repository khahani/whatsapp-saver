package com.khahani.whatsapp.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.khahani.whatsapp.firebase.analytic.screen.TrackableScreen;

public abstract class BaseFragment extends Fragment implements TrackableScreen {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
