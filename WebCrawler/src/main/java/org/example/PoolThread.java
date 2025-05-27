package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class PoolThread implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(PoolThread.class);

    private BlockingQueue<String> bq;
    public  PoolThread(BlockingQueue<String> bq){
        this.bq = bq;
    }
    @Override
    public void run() {
        while(true){
            String url = null;
            try {
                url = bq.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            crawl(url);
        }
    }
    public void crawl(String url)  {
        if(url != null && url.isEmpty()){
            return;
        }
       logger.info("Crawling : {}" , url);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("the title of the website " + doc.title());

        Elements links = doc.select("a[href]");

        for(Element link : links){
            String textUrl = link.attr("href");
            String text = link.text();
            try {
                bq.put(textUrl);
                logger.info("the text url : " + textUrl);
                logger.info("the text  : " + text);
                logger.info("---");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

    }
}
