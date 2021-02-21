package com.whatsappear.creator.firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.khahani.firebase.InAppMessageImpl;
import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.InAppMessage;
import com.whatsappear.BuildConfig;

public class InAppMessageCreator extends Creator<InAppMessage> {
    private final Context context;
    private InAppMessage inAppMessage;

    public InAppMessageCreator(Context context) {
        this.context = context;
    }

    @Override
    public InAppMessage factoryMethod() {
        // return new NullInAppMessage();
        inAppMessage = new InAppMessageImpl(() -> {
            String deviceId = inAppMessage.getDeviceId();
            if (BuildConfig.DEBUG) {
                Log.d("Khahani", "Device Id: " + deviceId);
                Toast.makeText(context, "Device Id: " + deviceId, Toast.LENGTH_LONG)
                        .show();
            }
        });
        return inAppMessage;
    }
}
