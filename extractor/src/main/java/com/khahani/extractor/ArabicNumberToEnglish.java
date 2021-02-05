package com.khahani.extractor;

public class ArabicNumberToEnglish implements Runnable {

    private String text;

    private void arabicToDecimal() {
        char[] chars = new char[text.length()];
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        text = new String(chars);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        arabicToDecimal();
    }
}