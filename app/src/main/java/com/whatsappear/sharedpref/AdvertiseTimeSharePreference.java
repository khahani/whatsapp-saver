package com.whatsappear.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

//khahani: move it to storage module {task time 1/3}
public class AdvertiseTimeSharePreference {
    private final Context context;
    private final String LAST_SEEN_KEY = "last_seen";
    private final String Time_KEY = "time_key";

    public AdvertiseTimeSharePreference(Context context) {
        this.context = context;
    }

    public long getLastSeen() {
        return getSharedPreferences().getLong(LAST_SEEN_KEY, 0L);
    }

//    public void setLastSeen(long time) {
//        getSharedPreferences().edit().putLong(LAST_SEEN_KEY, time).apply();
//    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(
                Time_KEY, Context.MODE_PRIVATE);
    }
}
