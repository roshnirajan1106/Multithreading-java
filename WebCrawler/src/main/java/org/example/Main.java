package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Set<String> visitedUrls = new HashSet<>();
    private static final Queue<String> urlsToVisit = new LinkedList<>();

    public static void main(String[] args) {
        String urlToStart = "https://example.com";
        urlsToVisit.add(urlToStart);
        int crawled = 0;
        int maxCrawl = 5;
        while (!urlsToVisit.isEmpty() && crawled < maxCrawl) {
            String urlToVisit = urlsToVisit.poll();
            if(visitedUrls.contains(urlToVisit)){
                continue;
            }
            boolean success = crawlPage(urlToVisit);
            if (success){
                crawled++;
            }

            System.out.println("=" + "=".repeat(50));
        }
    }

    public static boolean crawlPage(String url) {

        try {
            System.out.println("Crawling " + url);
            Document doc = null;
            doc = Jsoup.connect(url).timeout(5000).get();
            visitedUrls.add(url);
            System.out.println("the title of the website " + doc.title());
            Elements links = doc.select("a[href]");
            System.out.println("The number of links found in this doc is : " + links.size());
            int validUrlsAdded = 0;
            for (Element link : links) {
                String textUrl = link.attr("abs:href");
                if (isValid(textUrl) && !visitedUrls.contains(textUrl)) {
                    System.out.println("the valid urls : " + textUrl);
                    urlsToVisit.add(textUrl);
                    validUrlsAdded++;
                }
            }
            System.out.println("THe number of urls to process :" + validUrlsAdded);

            Thread.sleep(1000); // Wait 1 second between requests

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            System.out.println("issue while connecting to url " + e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean isValid(String url) {
        return url != null && !url.isEmpty() && (url.startsWith("https://") || url.startsWith("http://")) && !url.endsWith(".pdf") && !url.endsWith(".jpg") && !url.endsWith(".png") && !url.contains("#");
    }
}
