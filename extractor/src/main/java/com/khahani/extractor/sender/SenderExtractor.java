package com.khahani.extractor.sender;

public abstract class SenderExtractor {
    public static final String CONTACTS_GROUP = "c";
    protected String title;
    protected String sender;
    protected String group;

    public SenderExtractor(String title) {
        this.title = title;
    }

    public void extract() {
        if (isGroup()) {
            sender = extractSender();
            group = extractGroup();
        } else {
            sender = title;
            group = CONTACTS_GROUP;
        }
    }

    protected abstract String extractGroup();

    protected abstract String extractSender();

    protected abstract boolean isGroup();

    public String getSender() {
        return sender;
    }

    public String getGroup() {
        return group;
    }
}
