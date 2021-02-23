package com.whatsappear.creator.firebase;

import com.khahani.usecase_firebase.BuildConfig;
import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.InAppMessage;
import com.khahani.usecase_firebase.NullInAppMessage;

public class InAppMessageCreator extends Creator<InAppMessage> {

    @Override
    public InAppMessage factoryMethod() {
        if (BuildConfig.DEBUG) {
            return new NullInAppMessage();
        }
        try {
            return (InAppMessage) Class.forName("com.khahani.firebase.InAppMessageImpl")
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return new NullInAppMessage();
        }
    }
}
