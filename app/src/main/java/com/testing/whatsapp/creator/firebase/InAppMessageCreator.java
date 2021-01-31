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
//        inAppMessage = new InAppMessageImpl(() -> {
//            String deviceId = inAppMessage.getDeviceId();
//            Log.d("Khahani", deviceId);
//            Toast.makeText(context, "Device Id: " + deviceId, Toast.LENGTH_LONG)
//                    .show();
//        });
//        return inAppMessage;
    }
}
