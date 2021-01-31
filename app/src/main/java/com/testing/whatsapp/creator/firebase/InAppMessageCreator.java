package com.testing.whatsapp.creator.firebase;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.InAppMessage;
import com.khahani.usecase_firebase.NullInAppMessage;

public class InAppMessageCreator extends Creator<InAppMessage> {
    @Override
    public InAppMessage factoryMethod() {
        return new NullInAppMessage();
        //return new InAppMessageImpl(new InAppMessageUnderTest());
    }
}
