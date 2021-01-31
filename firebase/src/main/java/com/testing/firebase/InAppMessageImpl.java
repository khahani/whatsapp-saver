package com.testing.firebase;

import com.google.firebase.installations.FirebaseInstallations;
import com.khahani.usecase_firebase.InAppMessageBase;

public class InAppMessageImpl extends InAppMessageBase {

    public InAppMessageImpl(Runnable listener) {
        super(listener);
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
        if (BuildConfig.DEBUG)
            getId();
    }

}
