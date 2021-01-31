package com.testing.whatsapp.creator.firebase;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.InAppMessageBase;
import com.khahani.usecase_firebase.NullInAppMessage;

public class InAppMessageCreator extends Creator<InAppMessageBase> {
    @Override
    public InAppMessageBase factoryMethod() {
        return new NullInAppMessage();
        //return new InAppMessageImpl(new InAppMessageUnderTest());
    }
}
