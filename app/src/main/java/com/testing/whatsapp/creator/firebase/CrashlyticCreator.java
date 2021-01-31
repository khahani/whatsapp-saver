package com.testing.whatsapp.creator.firebase;

import com.khahani.usecase_firebase.Crashlytics;
import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.NullCrashlytics;

public class CrashlyticCreator extends Creator<Crashlytics> {

    @Override
    public Crashlytics factoryMethod() {
        return new NullCrashlytics();
        //return new Crashlytics();
    }
}
