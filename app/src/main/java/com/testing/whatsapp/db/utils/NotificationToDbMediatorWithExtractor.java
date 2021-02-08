package com.testing.whatsapp.db.utils;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.khahani.extractor.ArabicNumberToEnglish;
import com.khahani.extractor.Extractor;
import com.khahani.extractor.Filter;
import com.khahani.extractor.MessageEvaluator;
import com.khahani.extractor.RemoveRtlChar;
import com.khahani.extractor.SenderEvaluator;
import com.khahani.extractor.SenderExtractor;
import com.testing.whatsapp.db.ReceivedMessage;

import java.util.Locale;

public class NotificationToDbMediatorWithExtractor extends NotificationToDbMediatorBase {

    private long insertedId;

    public NotificationToDbMediatorWithExtractor(Context context, StatusBarNotification sbn) {
        super(context, sbn);
    }

    public boolean inserted() {
        return insertedId >= 0;
    }

    @Override
    public void insert() {
        try {
            String receivedSender = notification.getNotification().extras.getString("android.title");
            String receivedMessage = notification.getNotification().extras.getString("android.text");
            long postTime = notification.getPostTime();
            long date = System.currentTimeMillis();

            ArabicNumberToEnglish arEn = new ArabicNumberToEnglish();
            arEn.setText(receivedMessage);
            arEn.run();
            receivedMessage = arEn.getText();

            RemoveRtlChar rrtl = new RemoveRtlChar();
            rrtl.setText(receivedMessage);
            rrtl.run();
            receivedMessage = rrtl.getText();

            String language = Locale.getDefault().getLanguage();

            Filter f = new Filter();
            f.setUserLanguage(language);
            String filters = getFilters();
            f.setJson(filters);
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


            ex.run();

            ReceivedMessage rm = new ReceivedMessage();
            rm.sender = ex.getSender();
            rm.group = ex.getGroup();
            rm.text = ex.getMessage();
            rm.date = date;
            rm.postTime = postTime;

            insertedId = db.receivedMessageDao().insertMessage(rm);


        } catch (Extractor.MessageIsNotValid e) {
            Log.d("khahani", "Message is not valid" + e.getMessage());
        } catch (Exception e) {
            Log.d("khahani", e.getMessage());
        }
    }
}
