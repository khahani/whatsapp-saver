package com.khahani.usecase_firebase;

public class NullRemoteConfig extends RemoteConfig {
    @Override
    public void run() {

    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public void fetchAndActivate(OnCompletionListener onCompletionListener) {

    }
}
