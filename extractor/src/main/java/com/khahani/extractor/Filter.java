package com.khahani.extractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Filter implements Runnable {

    static final String LANGUAGE = "lan";
    static final String INVALID_SENDERS = "invalidSenders";
    static final String REGEX_SENDER_VALIDATOR = "regexSenderValidator";
    static final String INVALID_MESSAGES = "invalidMessages";
    static final String INVALID_MESSAGES_REGEX_REQUIRED = "invalidMessagesRegexRequired";

    private String json;
    private String language;
    private String[] invalidSenders;
    private String[] regexSenderValidator;
    private String[] invalidMessages;
    private String[] invalidMessagesRegexRequired;

    public Filter() {
    }

    void extractFilters() {
        try {
            JSONArray root = new JSONArray(json);
            for (int i = 0; i < root.length(); i++) {
                JSONObject filter = root.getJSONObject(i);
                language = filter.getString(LANGUAGE);
                invalidSenders = getFilters(filter.getJSONArray(INVALID_SENDERS));
                regexSenderValidator = getFilters(filter.getJSONArray(REGEX_SENDER_VALIDATOR));
                invalidMessages = getFilters(filter.getJSONArray(INVALID_MESSAGES));
                invalidMessagesRegexRequired = getFilters(filter.getJSONArray(INVALID_MESSAGES_REGEX_REQUIRED));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String[] getFilters(JSONArray filterJsonArray) throws JSONException {
        String[] filters = new String[filterJsonArray.length()];
        for (int i = 0; i < filterJsonArray.length(); i++) {
            filters[i] = filterJsonArray.getString(i);
        }
        return filters;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getLanguage() {
        return language;
    }

    public String[] getInvalidSenders() {
        return invalidSenders;
    }

    public String[] getRegexSenderValidator() {
        return regexSenderValidator;
    }

    public String[] getInvalidMessages() {
        return invalidMessages;
    }

    public String[] getInvalidMessagesRegexRequired() {
        return invalidMessagesRegexRequired;
    }

    @Override
    public void run() {
        extractFilters();
    }
}