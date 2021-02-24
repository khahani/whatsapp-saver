package com.whatsappear.creator.firebase;

import com.khahani.usecase_firebase.NullRemoteConfig;
import com.khahani.usecase_firebase.RemoteConfig;


public class RemoteConfigCreator extends ReleaseModuleActivator<RemoteConfig> {

    @Override
    protected RemoteConfig getNullModule() {
        return new NullRemoteConfig();
    }

    @Override
    protected RemoteConfig getReleaseModule() {
        try {
            return (RemoteConfig) Class.forName("com.khahani.firebase.RemoteConfigImpl")
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return new NullRemoteConfig();
        }
    }
}
