package com.khahani.whatsapp.firebase;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.khahani.whatsapp.R;

public class RemoteConfig implements Runnable {

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
