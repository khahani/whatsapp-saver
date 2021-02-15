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
import com.khahani.usecase_firebase.performance.Performance;
import com.khahani.usecase_firebase.performance.Trace;
import com.testing.firebase.performance.PerformanceImpl;
import com.testing.whatsapp.db.Db;
import com.testing.whatsapp.db.ReceivedMessage;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class NotificationToDbMediator extends NotificationToDbMediatorBase {

    public NotificationToDbMediator(Context context, StatusBarNotification sbn) {
        super(context, sbn);
    }

    @Override
    public synchronized void insert() {

        Performance p = new PerformanceImpl();
        String methodName = Objects.requireNonNull(new Object() {
        }.getClass().getEnclosingMethod()).getName();
        Trace t = p.newTrace(this.getClass().getName() + "." + methodName + "()");
        t.start();

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

            this.receivedMessage = new ReceivedMessage();
            this.receivedMessage.sender = ex.getSender();
            this.receivedMessage.group = ex.getGroup();
            this.receivedMessage.text = ex.getMessage();
            this.receivedMessage.date = date;
            this.receivedMessage.postTime = postTime;

            if (exists())
                return;

            insertedId = db.receivedMessageDao().insertMessage(this.receivedMessage);


        } catch (Extractor.MessageIsNotValid e) {
            Log.d("khahani", "Message is not valid" + e.getMessage());
        } catch (Exception e) {
            Log.d("khahani", e.getMessage());
        }

        t.stop();
    }

    private boolean exists() {
        List<ReceivedMessage> chats = Db.getInstance(context).receivedMessageDao().getChatsSync(receivedMessage.sender);

        for (ReceivedMessage rm : chats) {
            if (rm.postTime == receivedMessage.postTime && rm.text.equals(receivedMessage.text)) {
                Log.d("Khahani", "It was in the stack");
                return true;
            }
        }
        return false;
    }
}
