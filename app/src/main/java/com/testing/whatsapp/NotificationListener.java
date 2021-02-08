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

import java.util.ArrayList;


public class NotificationListener extends NotificationListenerService {

    private final ArrayList<StatusBarNotification> lastStatusBarNotifications = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d("Khahani", "onNotificationPosted:\n");
        int notificationCode = matchNotificationCode(sbn);

        if (notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {
            if (!isValidNotification(sbn)) {
                Log.d("Khahani", "Notification is not valid.\n");
                return;
            }
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

    private boolean isValidNotification(StatusBarNotification sbn) {
        boolean VALID = true;
        boolean INVALID = false;

        if (lastStatusBarNotifications.isEmpty()) {
            lastStatusBarNotifications.add(sbn);
            return VALID;
        }

        for (StatusBarNotification lastStatusBarNotification : lastStatusBarNotifications) {
            String newSender = sbn.getNotification().extras.getString("android.title");
            String newText = sbn.getNotification().extras.getString("android.text");
            String lastSender = lastStatusBarNotification.getNotification().extras.getString("android.title");
            String lastText = lastStatusBarNotification.getNotification().extras.getString("android.text");

//            if (sbn.getPostTime() - lastStatusBarNotification.getPostTime() < 1000)
//                if (newSender.equals(lastSender) && newText.equals(lastText))
//                    return INVALID;
            if (newSender.equals(lastSender) && newText.equals(lastText))
                return INVALID;
        }

        lastStatusBarNotifications.add(sbn);
        return VALID;
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        int notificationCode = matchNotificationCode(sbn);

        Log.d("Khahani", "onNotificationRemoved()");

        if (notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {

            StatusBarNotification[] activeNotifications = this.getActiveNotifications();

            if (activeNotifications != null && activeNotifications.length > 0) {
                for (StatusBarNotification activeNotification : activeNotifications) {
                    if (notificationCode == matchNotificationCode(activeNotification)) {
                        lastStatusBarNotifications.clear();
                        break;
                    }
                }
            }
        }
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