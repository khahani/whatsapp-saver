package com.whatsappear.db.utils;

import android.content.Context;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.khahani.extractor.ArabicNumberToEnglish;
import com.khahani.extractor.Extractor;
import com.khahani.extractor.Filter;
import com.khahani.extractor.HumanMessageValidation;
import com.khahani.extractor.MessageEvaluator;
import com.khahani.extractor.RemoveRtlChar;
import com.khahani.extractor.sender.SenderEvaluator;
import com.khahani.extractor.sender.SenderExtractor;
import com.khahani.extractor.sender.SenderExtractorWithAtSign;
import com.khahani.extractor.sender.SenderExtractorWithColon;
import com.khahani.usecase_firebase.performance.Performancable;
import com.khahani.usecase_firebase.performance.TrackerKeyMaker;
import com.whatsappear.db.Db;
import com.whatsappear.db.ReceivedMessage;

import java.util.List;
import java.util.Locale;

public class NotificationToDbMediator extends NotificationToDbMediatorBase implements Performancable {

    public NotificationToDbMediator(Context context, StatusBarNotification sbn) {
        super(context, sbn);
    }

    @Override
    protected void insert() {

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

            SenderExtractor sx;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P)
                sx = new SenderExtractorWithAtSign(receivedSender);
            else
                sx = new SenderExtractorWithColon(receivedSender);

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

    }

    private boolean exists() {
        List<ReceivedMessage> messages = Db.getInstance(context).receivedMessageDao().getMessagesSync(receivedMessage.sender);

        HumanMessageValidation hv = new HumanMessageValidation();
        hv.setSecondTime(receivedMessage.postTime);

        for (ReceivedMessage rm : messages) {
            hv.setFirstTime(rm.postTime);
            hv.run();
            if (hv.isHuman() && rm.text.equals(receivedMessage.text) && rm.group.equals(receivedMessage.group)) {
                Log.d("Khahani", "It was in the stack");
                return true;
            }
        }
        return false;
    }
    @Override
    public void run() {
        insert();
    }

    @Override
    public String getTrackerKey() {
        TrackerKeyMaker t = TrackerKeyMaker.getInstance(this, "insert");
        t.run();
        return t.getKey();
    }
}
