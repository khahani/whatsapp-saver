package com.testing.whatsapp.firebase;

import android.util.Log;

import com.google.firebase.BuildConfig;
import com.google.firebase.installations.FirebaseInstallations;

public class InAppMessage implements Runnable {

    private void getId() {
        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> Log.d("Khahani", task.getResult()));
    }

    @Override
    public void run() {
        //khahani: determine a better condition after build process optimized.
        if (BuildConfig.DEBUG)
            getId();
    }
}
