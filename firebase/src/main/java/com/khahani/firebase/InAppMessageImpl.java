package com.khahani.firebase;

import android.util.Log;

import com.google.firebase.installations.FirebaseInstallations;
import com.khahani.usecase_firebase.InAppMessage;

public class InAppMessageImpl extends InAppMessage {

    private void getId() {
        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            uniqueId = task.getResult();
            Log.d("Khahani", "InAppMessage Device Id: " + task.getResult());
        });
    }

    @Override
    public void run() {
        if (BuildConfig.DEBUG)
            getId();
    }

}
