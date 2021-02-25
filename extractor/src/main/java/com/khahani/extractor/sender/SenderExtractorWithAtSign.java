package com.khahani.extractor.sender;

public class SenderExtractorWithAtSign extends SenderExtractor {

    public SenderExtractorWithAtSign(String title) {
        super(title);
    }

    @Override
    protected String extractGroup() {
        return title.split("@")[1].trim();
    }

    @Override
    protected String extractSender() {
        return title.split("@")[0].trim();
    }

    @Override
    protected boolean isGroup() {
        return title.contains("@") && title.split("@").length == 2;
    }

}
