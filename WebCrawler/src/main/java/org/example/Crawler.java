package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Crawler {
    private String url;

    public Crawler(String url){
        this.url = url;
    }
    public void crawl() throws IOException {
        System.out.println("Crawling " + url);
        Document doc = Jsoup.connect(url).get();
        System.out.println("the title of the website " + doc.title());

        Elements links = doc.select("a[href]");

        for(Element link : links){
            String textUrl = link.attr("href");
            String text = link.text();
            System.out.println("the text url : " + textUrl);
            System.out.println("the text  : " + text);
            System.out.println("---");

        }

    }
}
