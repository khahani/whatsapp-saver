package com.whatsappear;

import android.content.Context;

import com.whatsappear.sharedpref.AdvertiseTimeSharePreference;

public class AdvertiseTimeCommand implements Runnable {
    private final long CONST_DURATION = 3600000L;
    private final AdvertiseTimeSharePreference advertiseTimeSharePreference;

    public AdvertiseTimeCommand(Context context) {
        advertiseTimeSharePreference = new AdvertiseTimeSharePreference(context);
    }

    public boolean shouldShow() {
        return System.currentTimeMillis() - getLastSeen() > CONST_DURATION;
    }

    private void calculate() {
        if (shouldShow()) {
            save();
        }
    }

    private long getLastSeen() {
        return advertiseTimeSharePreference.getLastSeen();
    }

    private void save() {
        advertiseTimeSharePreference.setLastSeen(System.currentTimeMillis());
    }

    @Override
    public void run() {
        calculate();
    }
}
