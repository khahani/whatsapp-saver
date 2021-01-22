package com.khahani.whatsappshowdeletedmessages.Model.db.utils.scrapping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Scrapping implements Runnable {
    private final String url;
    private final CompletedListener listener;

    public Scrapping(String url, CompletedListener listener) {
        this.url = url;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            String title = "", description = "", imageUrl = "";

            Document doc = Jsoup.connect(url).get();
            Elements allMeta = doc.getElementsByTag("meta");
            for (Element m : allMeta) {
                String p = m.attr("property");
                switch (p) {
                    case "og:title":
                        title = m.attr("content");
                        break;
                    case "og:description":
                        description = m.attr("content");
                        break;
                    case "og:image":
                        imageUrl = m.attr("content");
                        break;
                }

                boolean scraped = title.isEmpty() || description.isEmpty() || imageUrl.isEmpty();
                if (!scraped)
                    break;
            }

            listener.onCompleted(title, description, imageUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface CompletedListener {
        void onCompleted(String title, String description, String imageUrl);
    }
}
