package com.khahani.extractor.sender;

public class SenderExtractorWithColon extends SenderExtractor {
    public SenderExtractorWithColon(String title) {
        super(title);
    }

    @Override
    protected String extractGroup() {
        return null;
    }

    @Override
    protected String extractSender() {
        return null;
    }

    @Override
    protected boolean isGroup() {
        return false;
    }
}
