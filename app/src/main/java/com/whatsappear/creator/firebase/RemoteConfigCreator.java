package com.whatsappear.creator.firebase;

import com.khahani.firebase.RemoteConfigImpl;
import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.RemoteConfig;


public class RemoteConfigCreator extends Creator<RemoteConfig> {
    @Override
    public RemoteConfig factoryMethod() {
        // return new NullRemoteConfig();
        return new RemoteConfigImpl();
    }
}
