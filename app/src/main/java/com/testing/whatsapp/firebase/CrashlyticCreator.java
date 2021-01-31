package com.testing.whatsapp.firebase;

import com.khahani.usecase_firebase.CrashlyticsBase;
import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.NullCrashlytics;

public class CrashlyticCreator extends Creator<CrashlyticsBase> {

    @Override
    public CrashlyticsBase factoryMethod() {
        return new NullCrashlytics();
    }
}
