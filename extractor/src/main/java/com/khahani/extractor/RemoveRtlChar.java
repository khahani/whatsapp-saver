package com.khahani.extractor;

public class RemoveRtlChar implements Runnable {
    private String text;

    @Override
    public void run() {
        remove();
    }

    private void remove() {
        StringBuilder result = new StringBuilder();
        char[] chars = text.toCharArray();
        for (char aChar : chars) {
            if (aChar != '\u200F')
                result.append(aChar);
        }
        text = result.toString();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
