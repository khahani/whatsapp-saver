package com.whatsappear.creator.firebase;

import com.khahani.usecase_firebase.Crashlytics;
import com.khahani.usecase_firebase.NullCrashlytics;
import com.whatsappear.BuildConfig;

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
        if (BuildConfig.ENABLE_CRASHLYTICS)
            return getRealCrashlytics();
        else
            return new NullCrashlytics();
    }

    private Crashlytics getRealCrashlytics() {
        try {
            return (Crashlytics) Class.forName("com.khahani.firebase.CrashlyticsImpl")
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return new NullCrashlytics();
        }
    }
}
