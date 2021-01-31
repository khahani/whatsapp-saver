package com.testing.whatsapp.creator.firebase;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.RemoteConfig;
import com.testing.firebase.RemoteConfigImpl;

public class RemoteConfigCreator extends Creator<RemoteConfig> {
    @Override
    public RemoteConfig factoryMethod() {
//        return new NullRemoteConfig();
        return new RemoteConfigImpl();
    }
}
