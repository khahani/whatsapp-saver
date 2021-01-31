package com.testing.whatsapp.creator.firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.khahani.usecase_firebase.InAppMessage;

public class InAppMessageUnderTest implements Runnable {
    private final Context context;
    private final InAppMessage inAppMessage;

    public InAppMessageUnderTest(Context context, InAppMessage inAppMessage) {
        this.context = context;
        this.inAppMessage = inAppMessage;
    }


    @Override
    public void run() {
        String deviceId = inAppMessage.getDeviceId();
        Log.d("Khahani", deviceId);
        Toast.makeText(context, deviceId, Toast.LENGTH_LONG).show();
    }
}
