package com.khahani.whatsappshowdeletedmessages.Model.db.utils;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.khahani.whatsappshowdeletedmessages.Model.db.Db;
import com.khahani.whatsappshowdeletedmessages.Model.db.ReceivedMessage;

public class NotificationToDbMediator {

    private final String[] failedMessages = new String[]{
            "Checking for new messages",
            "%d new messages",
            "\uD83D\uDCF9 Incoming video call"
    };
    private final StatusBarNotification notification;
    private final Db db;

    public NotificationToDbMediator(Context context, StatusBarNotification sbn) {
        this.notification = sbn;
        this.db = Db.getInstance(context);
    }

    public void insert() {
        ReceivedMessage rm = new ReceivedMessage();
        rm.sender = notification.getNotification().extras.getString("android.title");
        rm.text = notification.getNotification().extras.getString("android.text");
        rm.date = System.currentTimeMillis();

        StringBuilder logMessage = new StringBuilder();
        logMessage.append(String.format("insert() : \nsender = %s\n", rm.sender));

        if (!isSenderValid(rm.sender)) {
            logMessage.append(String.format("insert() : %s [is not valid sender]\n", rm.text));
            return;
        }

        if (!isValidMessage(rm.text)) {
            logMessage.append(String.format("insert() : %s [is not valid message]\n", rm.text));
            return;
        } else {
            logMessage.append(String.format("text = %s\n", rm.text));
        }

        rm.sender = removeWhatsappThingsFromSender(rm.sender);
        logMessage.append(String.format("sender after optimizing: %s\n", rm.sender));

        logMessage.append(String.format("date: %s\n", rm.date));

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

    private boolean isSenderValid(String sender) {
        boolean VALID = true, INVALID = false;

        if (sender.equals("WhatsApp")) {
            return INVALID;
        }

        return VALID;
    }

    private String removeWhatsappThingsFromSender(String sender) {
        if (sender.startsWith("(messages")) {
            sender = sender.replaceFirst("\\(messages [0-9]\\)", "");
            //todo: check unread messages with 2 digits format
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
