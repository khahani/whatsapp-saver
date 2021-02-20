package com.whatsappear.db.utils;

import android.content.Context;
import android.service.notification.StatusBarNotification;

import com.khahani.usecase_firebase.performance.Performancable;
import com.whatsappear.db.Db;
import com.whatsappear.db.ReceivedMessage;
import com.whatsappear.sharedpref.RemoteConfigDefault;

public abstract class NotificationToDbMediatorBase implements Runnable, Performancable {
    protected final StatusBarNotification notification;
    protected final Db db;
    protected final Context context;
    protected long insertedId;
    protected ReceivedMessage receivedMessage;

    public NotificationToDbMediatorBase(Context context, StatusBarNotification sbn) {
        this.notification = sbn;
        this.db = Db.getInstance(context);
        this.context = context;
    }

    public String getFilters() {
        return new RemoteConfigDefault(context).getFilters();
    }

    public boolean inserted() {
        return insertedId > 0;
    }

    public ReceivedMessage getReceivedMessage() {
        return this.receivedMessage;
    }

    protected abstract void insert();

    @Override
    public void run() {
        insert();
    }
}
