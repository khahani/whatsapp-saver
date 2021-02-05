package com.testing.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.khahani.usecase_firebase.OnCompletionListener;
import com.khahani.usecase_firebase.RemoteConfig;

public class RemoteConfigImpl extends RemoteConfig {

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private void enable() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(BuildConfig.DEBUG ? 60 : 86400)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_default);
    }

    @Override
    public void run() {
        enable();
    }

    @Override
    public String getString(String key) {
        return mFirebaseRemoteConfig.getString(key);
    }

    @Override
    public void fetchAndActivate(OnCompletionListener onCompletionListener) {
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        try {
                            onCompletionListener.onComplete(task.getResult());
                        } catch (Exception e) {
                            onCompletionListener.onComplete(false);
                        }
                    }
                });
    }
}
