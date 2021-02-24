package com.whatsappear.creator.firebase;

import android.content.Context;

import com.khahani.usecase_firebase.InAppMessage;
import com.khahani.usecase_firebase.NullInAppMessage;

import java.lang.reflect.Constructor;

public class InAppMessageCreator extends ReleaseModuleActivator<InAppMessage> {

    private final Context context;

    public InAppMessageCreator(Context context) {
        this.context = context;
    }

    @Override
    protected InAppMessage getNullModule() {
        return new NullInAppMessage();
    }

    @Override
    protected InAppMessage getReleaseModule() {
        try {
            Constructor<?> c = Class.forName("com.khahani.firebase.InAppMessageImpl")
                    .getConstructor(Context.class);
            return (InAppMessage) c.newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
            return new NullInAppMessage();
        }
    }
}
