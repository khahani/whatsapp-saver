package com.testing.whatsapp.creator.firebase;

import android.content.Context;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.InAppMessage;
import com.khahani.usecase_firebase.NullInAppMessage;

public class InAppMessageCreator extends Creator<InAppMessage> {
    private final Context context;
    private InAppMessage inAppMessage;

    public InAppMessageCreator(Context context) {
        this.context = context;
    }

    @Override
    public InAppMessage factoryMethod() {
        return new NullInAppMessage();
    }
}
