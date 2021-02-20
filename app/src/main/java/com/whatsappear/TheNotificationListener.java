package com.whatsappear;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.khahani.usecase_firebase.performance.Performance;
import com.whatsappear.creator.firebase.PerformanceCreator;
import com.whatsappear.db.Db;
import com.whatsappear.db.utils.ChatToDbMediator;
import com.whatsappear.db.utils.NotificationToDbMediator;
import com.whatsappear.db.utils.NotificationToDbMediatorBase;


public class TheNotificationListener extends NotificationListenerService {

    private final Performance performance;

    public TheNotificationListener() {
        performance = new PerformanceCreator().factoryMethod();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        int notificationCode = matchNotificationCode(sbn);

        if (notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {

            NotificationToDbMediatorBase ndb = new NotificationToDbMediator(getApplicationContext(), sbn);
            performance.newTrace(ndb.getTrackerKey());
            performance.setMustTraceObject(ndb);
            performance.run();

            if (ndb.inserted()) {
                ChatToDbMediator ctd = new ChatToDbMediator(Db.getInstance(getApplicationContext()));
                ctd.setChat(ndb.getReceivedMessage());

                performance.newTrace(ctd.getTrackerKey());
                performance.setMustTraceObject(ctd);
                performance.run();
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