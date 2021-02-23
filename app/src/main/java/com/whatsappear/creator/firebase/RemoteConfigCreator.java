package com.whatsappear.creator.firebase;

import com.khahani.usecase_firebase.BuildConfig;
import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.NullRemoteConfig;
import com.khahani.usecase_firebase.RemoteConfig;


public class RemoteConfigCreator extends Creator<RemoteConfig> {
    @Override
    public RemoteConfig factoryMethod() {
        if (BuildConfig.DEBUG) {
            return new NullRemoteConfig();
        }
        try {
            return (RemoteConfig) Class.forName("com.khahani.firebase.RemoteConfigImpl")
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return new NullRemoteConfig();
        }
    }
}
