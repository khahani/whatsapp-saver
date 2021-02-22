package com.khahani.extractor.sender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SenderEvaluator {
    private String[] regexSenderValidator;
    private String[] invalidSenders;
    private String sender;

    public void setRegexSenderValidator(String[] regexSenderValidator) {
        this.regexSenderValidator = regexSenderValidator;
    }

    public void setInvalidSenders(String[] invalidSenders) {
        this.invalidSenders = invalidSenders;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isValid() {
        if (containWrongSender())
            return false;
        return !containWrongRegexSender();
    }

    private boolean containWrongRegexSender() {
        for (String regx : regexSenderValidator) {
            Pattern p = Pattern.compile(regx);
            Matcher m = p.matcher(sender);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

    private boolean containWrongSender() {
        for (String invalidSender : invalidSenders) {
            if (sender.equals(invalidSender)) {
                return true;
            }
        }
        return false;
    }
}
