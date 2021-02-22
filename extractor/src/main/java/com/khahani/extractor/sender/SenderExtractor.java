package com.khahani.extractor.sender;

public class SenderExtractor {
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

    private String extractGroup() {
        return title.split("@")[1].trim();
    }

    private String extractSender() {
        return title.split("@")[0].trim();
    }

    private boolean isGroup() {
        return title.contains("@") && title.split("@").length == 2;
    }

    public String getSender() {
        return sender;
    }

    public String getGroup() {
        return group;
    }
}
