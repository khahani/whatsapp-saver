package com.khahani.extractor.sender;

public class SenderExtractorWithColon extends SenderExtractor {
    public SenderExtractorWithColon(String title) {
        super(title);
    }

    @Override
    protected String extractGroup() {
        return getTitleWithoutMessageCounter().split(":")[0].trim();
    }

    @Override
    protected String extractSender() {
        return getTitleWithoutMessageCounter().split(":")[1].trim();
    }

    private String getTitleWithoutMessageCounter() {
        return title.replaceAll("[(](.*?)[)]", "");
    }

    @Override
    protected boolean isGroup() {
        return title.contains(":") && title.split(":").length == 2;
    }
}
