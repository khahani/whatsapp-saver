package com.khahani.extractor;

public class Extractor implements Runnable {
    protected String group;
    protected String sender;
    protected String message;
    private SenderEvaluator senderEvaluator;
    private MessageEvaluator messageEvaluator;
    private SenderExtractor senderExtractor;

    private void extract() {

        senderExtractor.extract();
        group = senderExtractor.getGroup();
        sender = senderExtractor.getSender();

        senderEvaluator.setSender(sender);

        if (senderEvaluator.isValid()) {

            if (messageEvaluator.isValid()) {
                message = messageEvaluator.getMessage();
            } else {
                throw new MessageIsNotValid();
            }
        } else {
            throw new MessageIsNotValid();
        }
    }

    @Override
    public void run() {
        extract();
    }

    public String getSender() {
        return this.sender;
    }

    public String getGroup() {
        return this.group;
    }

    public String getMessage() {
        return this.message;
    }

    public void setSenderEvaluator(SenderEvaluator senderEvaluator) {
        this.senderEvaluator = senderEvaluator;
    }

    public void setMessageEvaluator(MessageEvaluator messageEvaluator) {
        this.messageEvaluator = messageEvaluator;
    }

    public void setSenderExtractor(SenderExtractor senderExtractor) {
        this.senderExtractor = senderExtractor;
    }

    public static class MessageIsNotValid extends RuntimeException {
    }
}
