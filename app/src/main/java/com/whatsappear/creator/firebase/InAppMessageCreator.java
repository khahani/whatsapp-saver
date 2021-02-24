package com.whatsappear.creator.firebase;

import android.content.Context;

import com.khahani.usecase_firebase.BuildConfig;
import com.khahani.usecase_firebase.InAppMessage;
import com.khahani.usecase_firebase.NullInAppMessage;
import com.khahani.usecase_firebase.creator.Creator;

import java.lang.reflect.Constructor;

public class InAppMessageCreator extends Creator<InAppMessage> {

    private final Context context;

    public InAppMessageCreator(Context context) {
        this.context = context;
    }

    @Override
    public InAppMessage factoryMethod() {
        if (BuildConfig.DEBUG) {
            try {
                Constructor<?> c = Class.forName("com.khahani.firebase.InAppMessageImpl")
                        .getConstructor(Context.class);
                return (InAppMessage) c.newInstance(context);
            } catch (Exception e) {
                e.printStackTrace();
                return new NullInAppMessage();
            }
        }

        return new NullInAppMessage();
    }
}
