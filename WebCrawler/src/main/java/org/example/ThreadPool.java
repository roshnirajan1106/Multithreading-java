package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPool {
    int tasks;
    int maxNoOfThreads;
    private BlockingQueue<String> bq = null;
    private List<Thread> threadPoolList= new ArrayList<>(maxNoOfThreads);
    private String url;
    public ThreadPool(int tasks, int threads,String url){
        this.tasks= tasks;
        this.maxNoOfThreads = threads;
        this.url = url;
        bq = new ArrayBlockingQueue<>(tasks);
        for(int i = 0; i <maxNoOfThreads ;i++){
            threadPoolList.add(new Thread(new PoolThread(bq)));
        }
        for(int i = 0;i <maxNoOfThreads;i++){
            threadPoolList.get(i).start();
        }

    }

    public void execute(){
        try {
            bq.put(url);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
