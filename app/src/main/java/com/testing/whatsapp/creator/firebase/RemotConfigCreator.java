package com.testing.whatsapp.creator.firebase;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.NullRemoteConfig;
import com.khahani.usecase_firebase.RemoteConfig;

public class RemotConfigCreator extends Creator<RemoteConfig> {
    @Override
    public RemoteConfig factoryMethod() {
        return new NullRemoteConfig();
    }
}
