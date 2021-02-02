package com.testing.whatsapp.usecases;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageEvaluator {

    private String[] invalidMessages;
    private String[] regexMessageValidators;
    private String message;

    public boolean isValid() {
        return checkWithFixedMessages() && checkWithRegexMessages();
    }

    private boolean checkWithRegexMessages() {
        for (String regx : regexMessageValidators) {
            Pattern p = Pattern.compile(regx);
            Matcher m = p.matcher(message);
            if (m.find())
                return false;
        }
        return true;
    }

    private boolean checkWithFixedMessages() {
        for (String iv : invalidMessages)
            if (message.equals(iv))
                return false;
        return true;
    }

    public void setInvalidMessages(String[] invalidMessages) {
        this.invalidMessages = invalidMessages;
    }

    public void setRegexMessageValidators(String[] regexMessageValidators) {
        this.regexMessageValidators = regexMessageValidators;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
