package com.testing.firebase;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.khahani.usecase_firebase.RemoteConfigBase;

public class RemoteConfig extends RemoteConfigBase {

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private void enable() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_default);
    }

    @Override
    public void run() {
        enable();
    }
}
