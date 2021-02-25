package com.khahani.firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.installations.FirebaseInstallations;
import com.khahani.usecase_firebase.InAppMessage;

public class InAppMessageImpl extends InAppMessage {

    public InAppMessageImpl(Context context) {
        super(context);
    }

    private void getId() {
        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            uniqueId = task.getResult();
            if (BuildConfig.DEBUG) {
                Log.d("Khahani", "InAppMessage Device Id: " + task.getResult());
                Toast.makeText(context, String.format("InAppMessages Device Id: %s", uniqueId), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public void run() {
        getId();
    }

}
