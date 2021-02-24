package com.whatsappear.creator.firebase;

import com.khahani.usecase_firebase.creator.Creator;
import com.whatsappear.BuildConfig;

public abstract class ReleaseModuleActivator<T> extends Creator<T> {
    @Override
    public T factoryMethod() {
        if (BuildConfig.ENABLE_RELEASE_MODULES)
            return getReleaseModule();

        return getNullModule();
    }

    protected abstract T getNullModule();

    protected abstract T getReleaseModule();
}
