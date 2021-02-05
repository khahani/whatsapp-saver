package com.khahani.extractor;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterFarsiTest {

    private final ArabicNumberToEnglish arabicNumberToEnglish = new ArabicNumberToEnglish();

    @Test
    public void t() {

        String regx = "\u200F\u200F([0-9]|[0-9][0-9]|[0-9][0-9][0-9]) پيام در ([0-9]|[0-9][0-9]|[0-9][0-9][0-9]) گفتگو";
        String text = "\u200F\u200F۲۶ پيام در ۳ گفتگو";
        arabicNumberToEnglish.setText(text);
        arabicNumberToEnglish.run();
        text = arabicNumberToEnglish.getText();

        Pattern p = Pattern.compile(regx);
        Matcher m = p.matcher(text);
        Assert.assertTrue(String.format("match: %s", text), m.find());

    }
}
