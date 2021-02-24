package com.whatsappear.creator.firebase;

import com.khahani.usecase_firebase.Crashlytics;
import com.khahani.usecase_firebase.NullCrashlytics;

public class CrashlyticsCreator extends ReleaseModuleActivator<Crashlytics> {

    @Override
    protected Crashlytics getNullModule() {
        return getModule();
    }

    @Override
    protected Crashlytics getReleaseModule() {
        return getModule();
    }

    private Crashlytics getModule() {
        return new NullCrashlytics();
    }
}
