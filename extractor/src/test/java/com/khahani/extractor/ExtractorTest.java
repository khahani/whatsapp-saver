package com.khahani.extractor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(Enclosed.class)
public class ExtractorTest {

    //region fake data

    static final String language = "en";

    static final String[] invalidMessagesRegexRequired = new String[]{
            "2 new messages",
            //"3 messages from 2 chats", // the sender of this message is whatsapp so by default will removes.
            "2 missed voice calls",
            "3 missed calls"
    };
    // these messages should check with corresponding translated one.
    static String[] invalidMessages = new String[]{
            "null",
            "Checking for new messages",// the sender of this message is whatsapp so by default will removes.
            "Incoming voice call",
            "Incoming video call",
            "Missed voice call",
            "Missed video call",
            "\uD83D\uDCF7 Photo",
            "Calling…",
            "Ringing…",
            "Ongoing voice call",
            "Ongoing video call",
            "\uD83D\uDCF9 Incoming video call"
//                ,
//                "\uD83C\uDFA4 Voice message (0:06)",
//                "\uD83C\uDFA5 Video (0:36)"
    };
    static String[] regexMessageValidators = new String[]{
            "([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) new messages",
            "([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed voice calls",
            "([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed calls",
    };
    static String[] regexSenderValidator = new String[]{
            "([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed voice calls",
            "([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed video calls",
            "\\(messages ([0-9]|[0-9][0-9]|[0-9][0-9][0-9])\\)"
    };
    static String[] invalidSenders = new String[]{
            "WhatsApp", // and translated in different languages
            "null"
    };
    static String[] wrongSendersFakeData = new String[]{
            "WhatsApp", // and translated in different languages
            "null",
            "2 missed voice calls", // and translated
            "2 missed video calls", // if there is any (should test)
            "10 missed video calls",
            "152 missed video calls",
            "999 missed video calls",
            "(messages 2) E"
    };

    //endregion

    public static class SenderExtractorTest {

        //region fake data
        private final String[] titles = new String[]{
                "E @ Thebook",
                "حسین داداشی @ Distance",
                "E"
        };

        private final String[] groups = new String[]{
                "Thebook",
                "Distance",
                "c"
        };

        private final String[] senders = new String[]{
                "E",
                "حسین داداشی",
                "E"
        };

        //endregion

        @Test
        public void when_a_title_is_group_then_sender_and_group_extract_correctly() {
            for (int i = 0; i < titles.length; i++) {
                SenderExtractor s = new SenderExtractor(titles[i]);
                s.extract();
                Assert.assertEquals(senders[i], s.getSender());
                Assert.assertEquals(groups[i], s.getGroup());
            }
        }

    }

    public static class MessageTest {

        private ArrayList<String> wrongMessagesFakeData;
        private MessageEvaluator extractor;

        @Before
        public void construct() {
            initFakeData();
            initExtractor();
        }

        private void initFakeData() {
            wrongMessagesFakeData = new ArrayList<>();
            wrongMessagesFakeData.addAll(Arrays.asList(invalidMessages));
            wrongMessagesFakeData.addAll(Arrays.asList(invalidMessagesRegexRequired));
        }

        private void initExtractor() {
            extractor = new MessageEvaluator();
            extractor.setInvalidMessages(invalidMessages);
            extractor.setRegexMessageValidators(regexMessageValidators);
        }

        @Test
        public void check_message_is_valid() {
            for (int i = 0; i < wrongMessagesFakeData.size(); i++) {
                extractor.setMessage(wrongMessagesFakeData.get(i));
                assertMessageIsValid();
            }
        }

        private void assertMessageIsValid() {
            Assert.assertFalse(String.format("Message: %s", extractor.getMessage()), extractor.isValid());
        }
    }

    public static class SenderTest {

        private SenderEvaluator evaluator;

        @Before
        public void construct() {
            evaluator = new SenderEvaluator();
            evaluator.setInvalidSenders(invalidSenders);
            evaluator.setRegexSenderValidator(regexSenderValidator);
        }

        @Test
        public void wrong_sender_test() {
            for (String sender : wrongSendersFakeData) {
                evaluator.setSender(sender);
                assertSenderIsNotValid(sender);
            }
        }

        private void assertSenderIsNotValid(String sender) {
            Assert.assertFalse(String.format("sender: %s", sender), evaluator.isValid());
        }
    }

    public static class ExtractAlgorithmTest {

        private ArrayList<Message> possibleMessages;
        private Extractor extractor;

        private void initData() {
            possibleMessages = new ArrayList<>();
            possibleMessages.add(new Message("E", "Hi, I love you"));
            possibleMessages.add(new Message("E", "2 new messages"));
            possibleMessages.add(new Message("WhatsApp", "3 messages from 2 chats"));
            possibleMessages.add(new Message(null, null));
            possibleMessages.add(new Message("+98 902 570 4390", "سجاد"));
            possibleMessages.add(new Message("WhatsApp", "3 messages from 3 chats"));
            possibleMessages.add(new Message("WhatsApp", "Checking for new messages"));
            possibleMessages.add(new Message("+98 902 570 4390", "Incoming voice call"));
            possibleMessages.add(new Message("+98 902 570 4390", "Incoming video call"));
            possibleMessages.add(new Message("+98 902 570 4390", "Missed voice call"));
            possibleMessages.add(new Message("+98 902 570 4390", "Missed video call"));
            possibleMessages.add(new Message("E", "2 missed voice calls"));
            possibleMessages.add(new Message("E", "3 missed calls"));
            possibleMessages.add(new Message("سجاد", "\uD83C\uDFA4 Voice message (0:06)"));
            possibleMessages.add(new Message("+98 902 570 4390", "\uD83D\uDCF7 Photo"));
            possibleMessages.add(new Message("+98 902 570 4390", "\uD83D\uDE02\uD83D\uDE02")); // :D:D
            possibleMessages.add(new Message("+98 902 570 4390", "\uD83D\uDC9F Sticker")); // Big sticker
            possibleMessages.add(new Message("+98 902 570 4390", "\uD83C\uDFA5 Video (0:36)"));
            possibleMessages.add(new Message("مامان", "Calling…"));
            possibleMessages.add(new Message("مامان", "Ringing…"));
            possibleMessages.add(new Message("مامان", "Ongoing voice call"));
            possibleMessages.add(new Message("مامان", "Ongoing video call"));
            possibleMessages.add(new Message("E", "\uD83D\uDCF9 Incoming video call"));
            possibleMessages.add(new Message("E", "Missed video call"));
            possibleMessages.add(new Message("2 missed voice calls", "E, +98 902 570 4390"));
            possibleMessages.add(new Message("E", "https://upmedia.me/"));
            possibleMessages.add(new Message("واتساپ", "\u200F\u200F۲ پيام در ۲ گفتگو"));
            possibleMessages.add(new Message("واتساپ", "\u200F\u200F۳ پيام در ۲ گفتگو"));
            possibleMessages.add(new Message("E @ Thebook", "\uD83D\uDCF7 شیلنگ")); //Picture with caption in group
            possibleMessages.add(new Message("حسین داداشی @ Distance", "\uD83E\uDD26\u200D♂️\uD83E\uDD26\u200D♂️\uD83E\uDD26\u200D♂️☠"));
            possibleMessages.add(new Message("واتساب", "\u200F\u200F٥ رسائل من ٣ دردشات"));
            possibleMessages.add(new Message("E", "\u200Fمكالمة صوتية واردة"));
            possibleMessages.add(new Message("\u200Fمكالمتان صوتيتان فائتتان ٢", "\u200F\u200F\u202AE\u202C\u200F، \u200F\u202A+98 902 570 4390\u202C\u200F"));// When you have 2 missed calls from different contact
            possibleMessages.add(new Message("E", "\u200Fمكالمة صوتية جارية"));
            possibleMessages.add(new Message("حسین جوان بادصبا @ \uD83D\uDE80 موج همراه \uD83C\uDFE9", "مبارکه. \n" +
                    "فردا جهت تست نرم افزار گوشی شما به واحد پشتیبانی منتقل میشه\uD83D\uDE01"));
            possibleMessages.add(new Message("WhatsApp", "12 messages from 2 chats"));
            possibleMessages.add(new Message("(3 messages) E", ""));//khahani: this should be check with real device

            // When there is some messages that didn't see (unread messages), all messages ITERATES when A new message arrived [duplicate possiblitity]
        }

        @Before
        public void setup() {
            extractor = new Extractor();
        }

        @Test(expected = Extractor.MessageIsNotValid.class)
        public void wrong_receives_error_occurs() {
            String receivedSender = "WhatsApp";
            String receivedMessage = "Hi, I love you";

            init(receivedSender, receivedMessage);
            extractor.run();
        }

        @Test
        public void extract_information_from_received_information() {
            String receivedSender = "E";
            String receivedMessage = "Hi, I love you";
            String expectedSender = "E";
            String expectedGroup = "c";
            String expectedMessage = "Hi, I love you";

            init(receivedSender, receivedMessage);

            extractor.run();

            Assert.assertEquals("sender", expectedSender, extractor.getSender());
            Assert.assertEquals("group", expectedGroup, extractor.getGroup());
            Assert.assertEquals("message", expectedMessage, extractor.getMessage());

        }

        private void init(String receivedSender, String receivedMessage) {
            SenderExtractor senderExtractor = new SenderExtractor(receivedSender);
            extractor.setSenderExtractor(senderExtractor);

            SenderEvaluator senderEvaluator = new SenderEvaluator();
            senderEvaluator.setInvalidSenders(invalidSenders);
            senderEvaluator.setRegexSenderValidator(regexSenderValidator);
            extractor.setSenderEvaluator(senderEvaluator);

            MessageEvaluator messageEvaluator = new MessageEvaluator();
            messageEvaluator.setInvalidMessages(invalidMessages);
            messageEvaluator.setRegexMessageValidators(invalidMessagesRegexRequired);
            messageEvaluator.setMessage(receivedMessage);
            extractor.setMessageEvaluator(messageEvaluator);
        }

        public static class Message {
            private final String title;

            private final String body;

            public Message(String title, String body) {
                this.title = title;
                this.body = body;
            }

        }
    }

    public static class FilterTest {
        final String json = "[{\"lan\":\"en\",\"invalidSenders\":[\"WhatsApp\",\"null\"],\"regexSenderValidator\":[\"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed voice calls\",\"([2-9]|[1-9][0-9]|[1-9][0-9][0-9]) missed video calls\",\"\\\\(messages ([0-9]|[0-9][0-9]|[0-9][0-9][0-9])\\\\)\"],\"invalidMessages\":[\"null\",\"Checking for new messages\",\"Incoming voice call\",\"Incoming video call\",\"Missed voice call\",\"Missed video call\",\"\uD83D\uDCF7 Photo\",\"Calling…\",\"Ringing…\",\"Ongoing voice call\",\"Ongoing video call\",\"\uD83D\uDCF9 Incoming video call\"],\"invalidMessagesRegexRequired\":[\"2 new messages\",\"2 missed voice calls\",\"3 missed calls\"]}]";
        private final Filter filter = new Filter();

        @Test
        public void extract_items_from_json_test() {

            filter.setJson(json);
            filter.run();

            Assert.assertEquals(ExtractorTest.language, filter.getLanguage());
            Assert.assertArrayEquals(ExtractorTest.invalidSenders, filter.getInvalidSenders());
            Assert.assertArrayEquals(ExtractorTest.regexSenderValidator, filter.getRegexSenderValidator());
            Assert.assertArrayEquals(ExtractorTest.invalidMessages, filter.getInvalidMessages());
            Assert.assertArrayEquals(ExtractorTest.invalidMessagesRegexRequired, filter.getInvalidMessagesRegexRequired());
        }

    }

}
