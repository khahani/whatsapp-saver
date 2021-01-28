package com.testing.whatsapp.firebase;

import com.google.firebase.installations.FirebaseInstallations;

public class InAppMessage implements Runnable {

    private final Runnable listener;
    private String uniqueId;

    public InAppMessage(Runnable listener) {
        this.listener = listener;
    }

    private void getId() {
        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            uniqueId = task.getResult();
            listener.run();
        });
    }

    @Override
    public void run() {
        //khahani: determine a better condition after build process optimized.
        //if (BuildConfig.DEBUG)
        getId();
    }

    public String getDeviceId() {
        return uniqueId;
    }
}
