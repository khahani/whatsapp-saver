package com.testing.whatsapp.Model.db.utils;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.testing.whatsapp.BuildConfig;
import com.testing.whatsapp.Model.db.Db;
import com.testing.whatsapp.Model.db.ReceivedMessage;

import java.util.HashMap;
import java.util.Map;

public class NotificationToDbMediator {

    private final String[] failedMessages = new String[]{
            "Checking for new messages",
            "%d new messages",
            "\uD83D\uDCF9 Incoming video call"
    };
    private final StatusBarNotification notification;
    private final Db db;
    private final String GROUP_KEY = "group";
    private final String SENDER_KEY = "sender";

    public NotificationToDbMediator(Context context, StatusBarNotification sbn) {
        this.notification = sbn;
        this.db = Db.getInstance(context);
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

    public void insert() {
        ReceivedMessage rm = new ReceivedMessage();
        rm.sender = notification.getNotification().extras.getString("android.title");
        rm.text = notification.getNotification().extras.getString("android.text");
        rm.date = System.currentTimeMillis();

        StringBuilder logMessage = new StringBuilder();
        logMessage.append(String.format("insert() : \nsender = %s\n", rm.sender));

        //fixme: refactoring required
        if (!isValid(rm, logMessage)) return;

        prepareSenderAndGroup(rm, logMessage);

        logMessage.append(String.format("date: %s\n", rm.date));

        if (BuildConfig.DEBUG)
            Log.d("Khahani", logMessage.toString());

        Thread t = new Thread(() -> {
            try {
                long id = db.receivedMessageDao().insertMessage(rm);
                Log.d("Khahani", String.format("inserted: id = %d", id));
            } catch (Exception e) {
                Log.d("Khahani", e.getMessage());
                e.printStackTrace();
            }
        }, "dbThread");
        t.start();

    }

    private void prepareSenderAndGroup(ReceivedMessage rm, StringBuilder logMessage) {
        if (!isGroup(rm.sender)) {
            rm.sender = removeWhatsappThingsFromSender(rm.sender);
            logMessage.append(String.format("sender after optimizing: %s\n", rm.sender));
        } else {
            Map<String, String> groupAndSender = splitGroupFromSender(rm.sender);
            rm.group = groupAndSender.get(GROUP_KEY);
            rm.sender = groupAndSender.get(SENDER_KEY);
        }
    }

    private Map<String, String> splitGroupFromSender(String sender) {
        Map<String, String> split = new HashMap<>();
        String[] s = sender.split("@");
        split.put(GROUP_KEY, s[0]);
        split.put(SENDER_KEY, s[1]);
        return split;
    }

    private boolean isGroup(String sender) {
        return sender.indexOf("@") > 0;
    }

    private boolean isValid(ReceivedMessage rm, StringBuilder logMessage) {
        if (!isSenderValid(rm.sender)) {
            logMessage.append(String.format("insert() : %s [is not valid sender]\n", rm.text));
            return false;
        }

        if (!isValidMessage(rm.text)) {
            logMessage.append(String.format("insert() : %s [is not valid message]\n", rm.text));
            return false;
        } else {
            logMessage.append(String.format("text = %s\n", rm.text));
        }
        return true;
    }

    private boolean isSenderValid(String sender) {
        final boolean VALID = true, INVALID = false;

        if (sender.equals("WhatsApp"))
            return INVALID;
        //khahani: if contact name contains '@' symbole or group name contain '@' symbol we can't detect the message, for now.
        if (sender.split("@").length > 2)
            return INVALID;

        return VALID;
    }

    private String removeWhatsappThingsFromSender(String sender) {
        if (sender.startsWith("(messages")) {
            sender = sender.replaceFirst("\\(messages ([0-9]|[0-9][0-9])\\)", ""); //khahani: need to test
        }
        return sender;
    }

    private boolean isValidMessage(String text) {
        boolean INVALID = false;
        if (text == null)
            return INVALID;

        if (text.equals(failedMessages[0]))
            return INVALID;

        try {
            String[] s = text.split(" ");
            int number = Integer.parseInt(s[0]);
            if (number > 0) {
                if (s[1].equals("new") && s[2].equals("messages")) {
                    return INVALID;
                }
            }
        } catch (Exception ignored) {
        }

        return true;

    }
}
