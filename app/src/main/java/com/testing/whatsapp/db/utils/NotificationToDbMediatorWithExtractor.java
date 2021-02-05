package com.testing.whatsapp.db.utils;

import android.content.Context;
import android.service.notification.StatusBarNotification;

import com.khahani.extractor.Extractor;
import com.khahani.extractor.Filter;
import com.khahani.extractor.MessageEvaluator;
import com.khahani.extractor.SenderEvaluator;
import com.khahani.extractor.SenderExtractor;
import com.testing.whatsapp.db.ReceivedMessage;

import java.util.Locale;

public class NotificationToDbMediatorWithExtractor extends NotificationToDbMediatorBase {

    public NotificationToDbMediatorWithExtractor(Context context, StatusBarNotification sbn) {
        super(context, sbn);
    }


    @Override
    public void insert() {

        String receivedSender = notification.getNotification().extras.getString("android.title");
        String receivedMessage = notification.getNotification().extras.getString("android.text");
        long date = System.currentTimeMillis();

        String language = Locale.getDefault().getLanguage();

        Filter f = new Filter();
        f.setUserLanguage(language);
        f.setJson(getFilters());
        f.run();

        SenderExtractor sx = new SenderExtractor(receivedSender);

        SenderEvaluator se = new SenderEvaluator();
        se.setInvalidSenders(f.getInvalidSenders());
        se.setRegexSenderValidator(f.getRegexSenderValidator());

        MessageEvaluator me = new MessageEvaluator();
        me.setMessage(receivedMessage);
        me.setInvalidMessages(f.getInvalidMessages());
        me.setRegexMessageValidators(f.getRegexMessageValidators());

        Extractor ex = new Extractor();
        ex.setMessageEvaluator(me);
        ex.setSenderEvaluator(se);
        ex.setSenderExtractor(sx);

        try {
            ex.run();

            ReceivedMessage rm = new ReceivedMessage();
            rm.sender = ex.getSender();
            rm.group = ex.getGroup();
            rm.text = ex.getMessage();
            rm.date = date;

            db.receivedMessageDao().insertMessage(rm);

        } catch (Extractor.MessageIsNotValid e) {

        } catch (Exception e) {

        }
    }
}
