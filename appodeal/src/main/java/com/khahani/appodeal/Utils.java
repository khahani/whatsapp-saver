package com.khahani.appodeal;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

class Utils {

    static void showToast(Activity activity, String text) {
        if (BuildConfig.DEBUG) {
            Log.d("AppodealDemoApp", text);
            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
        }
    }

}
