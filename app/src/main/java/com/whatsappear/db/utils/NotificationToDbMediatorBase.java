package com.whatsappear.db.utils;

import android.content.Context;
import android.service.notification.StatusBarNotification;

import com.whatsappear.db.Db;
import com.whatsappear.db.ReceivedMessage;
import com.whatsappear.sharedpref.RemoteConfigDefault;

public abstract class NotificationToDbMediatorBase {
    protected final StatusBarNotification notification;
    protected final Db db;
    protected final String GROUP_KEY = "group";
    protected final String SENDER_KEY = "sender";
    protected final Context context;
    protected long insertedId;
    protected ReceivedMessage receivedMessage;

    public NotificationToDbMediatorBase(Context context, StatusBarNotification sbn) {
        this.notification = sbn;
        this.db = Db.getInstance(context);
        this.context = context;
    }

    //khahani: use it for the free time on next version
//    private void triggerActions(StatusBarNotification sbn) {
//
//        Bundle b = sbn.getNotification().extras;
//        Notification.Action[] actions = sbn.getNotification().actions;
//        Uri sound = sbn.getNotification().sound;
//        PendingIntent i = sbn.getNotification().contentIntent;
//        RemoteViews big = sbn.getNotification().bigContentView;
//
//    }
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
}
