package com.testing.whatsapp.creator.firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.InAppMessage;
import com.testing.firebase.BuildConfig;
import com.testing.firebase.InAppMessageImpl;

public class InAppMessageCreator extends Creator<InAppMessage> {
    private final Context context;
    private InAppMessage inAppMessage;

    public InAppMessageCreator(Context context) {
        this.context = context;
    }

    @Override
    public InAppMessage factoryMethod() {
//        return new NullInAppMessage();
        inAppMessage = new InAppMessageImpl(() -> {
            String deviceId = inAppMessage.getDeviceId();
            if (BuildConfig.DEBUG) {
                Log.d("Khahani", deviceId);
                Toast.makeText(context, "Device Id: " + deviceId, Toast.LENGTH_LONG)
                        .show();
            }
        });
        return inAppMessage;
    }
}
