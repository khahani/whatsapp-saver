package com.testing.whatsapp;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.testing.whatsapp.db.Db;
import com.testing.whatsapp.db.utils.ChatToDbMediator;
import com.testing.whatsapp.db.utils.NotificationToDbMediator;
import com.testing.whatsapp.db.utils.NotificationToDbMediatorBase;


public class NotificationListener extends NotificationListenerService {

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d("Khahani", "onNotificationPosted:\n");
        int notificationCode = matchNotificationCode(sbn);

        if (notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {
            Log.d("Khahani", "Notification is valid.\n");
            //khahani: use factory
            NotificationToDbMediatorBase ndb = new NotificationToDbMediator(getApplicationContext(), sbn);
            ndb.insert();

            if (ndb.inserted()) {
                ChatToDbMediator ctd = new ChatToDbMediator(Db.getInstance(getApplicationContext()));
                ctd.setChat(ndb.getReceivedMessage());
                ctd.run();
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    }

    private int matchNotificationCode(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();

        if (packageName.equals(ApplicationPackageNames.WHATSAPP_PACK_NAME)) {
            return (InterceptedNotificationCode.WHATSAPP_CODE);
        } else {
            return (InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE);
        }
    }

    private static final class ApplicationPackageNames {
        public static final String WHATSAPP_PACK_NAME = "com.whatsapp";
    }

    public static final class InterceptedNotificationCode {
        public static final int WHATSAPP_CODE = 2;
        public static final int OTHER_NOTIFICATIONS_CODE = 4; // We ignore all notification with code == 4
    }
}