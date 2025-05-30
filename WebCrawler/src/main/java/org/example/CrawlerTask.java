package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class CrawlerTask implements Runnable {
    private final Logger workerLogger = LoggerFactory.getLogger(CrawlerTask.class);
    private BlockingQueue<String> bq = null;
    private ConcurrentHashMap<String, Boolean> urlVisited = null;

    public CrawlerTask(BlockingQueue<String> bq, ConcurrentHashMap<String, Boolean> urlsVisited) {
        this.bq = bq;
        this.urlVisited = urlsVisited;
    }

    @Override
    public void run() {
        //this should always run unless the executor service has shutdown.

        while (!Thread.currentThread().isInterrupted()) {
            try {
                String url = bq.poll();
                if (url == null) {
                    workerLogger.info("No new tasks yet");
                    continue;
                }
                if (urlVisited.containsKey(url)) {
                    workerLogger.info("Url is already visited !");
                    continue;
                }
                workerLogger.info("Crawling the URL :{}" ,url);
                crawl(url);
                urlVisited.put(url,true);
            } catch (InterruptedException e) {
                workerLogger.info("Thread is interrupted! " + e.getMessage());
                break;
            } catch (IOException e) {
                workerLogger.info("Unable to connect to url!" + e.getMessage());
            }
        }
    }

    public void crawl(String url) throws IOException,InterruptedException {
        Document doc = Jsoup.connect(url).timeout(3000).get();
        Elements links = doc.select("a[href]");
        int numberOfThreadAdded = 0;
        for(Element link : links){
            String absoluteUrl = link.attr("abs:href");
            if(!isValidUrl(absoluteUrl) || urlVisited.containsKey(absoluteUrl) || bq.contains(absoluteUrl)) {
                continue;
            }
            if(numberOfThreadAdded > 2){
                break;
            }
            numberOfThreadAdded++;
            bq.put(absoluteUrl);
            workerLogger.info("Url added to queue :{}", absoluteUrl);
        }

    }

    public boolean isValidUrl(String url) {
        return url != null && !url.isEmpty() && (url.startsWith("https://") || url.startsWith("http://")) && !url.endsWith(".pdf") && !url.endsWith(".jpg") && !url.endsWith(".png") && !url.endsWith(".gif") && !url.endsWith(".css") && !url.endsWith(".js") && !url.contains("#") && !url.contains("mailto:") && url.length() < 200;

    }
}
