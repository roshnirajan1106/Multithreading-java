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
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args){

        BlockingQueue<String> bq = new ArrayBlockingQueue<>(20);
        ConcurrentHashMap<String , Boolean> urlsVisited = new ConcurrentHashMap<String, Boolean>();
        String startUrl = "https://example.com";
        bq.add(startUrl);
        ExecutorService es = Executors.newFixedThreadPool(10);
        for(int i = 0 ;i <10 ;i++){
            es.submit(new CrawlerTask(bq,urlsVisited));
        }
        try{
            es.shutdown();
            if(!es.awaitTermination(60,TimeUnit.SECONDS)){
                es.shutdownNow();
            }
            System.out.println("App shutdown successful!");
        }catch (InterruptedException e){
            System.out.println("Interrupted while waiting");
        }
    }
}
