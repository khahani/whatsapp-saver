package com.khahani.usecase_firebase;

public abstract class RemoteConfig implements Runnable {
    public abstract String getString(String key);

    public abstract void fetchAndActivate(OnCompletionListener onCompletionListener);
}
