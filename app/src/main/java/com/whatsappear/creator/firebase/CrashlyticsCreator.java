package com.whatsappear.creator.firebase;

import com.khahani.usecase_firebase.Crashlytics;
import com.khahani.usecase_firebase.NullCrashlytics;
import com.khahani.usecase_firebase.creator.Creator;

public class CrashlyticsCreator extends Creator<Crashlytics> {

    @Override
    public Crashlytics factoryMethod() {
        return new NullCrashlytics();
        // The line below only fire an exception that cause crash
//            return new CrashlyticsImpl();
    }
}
