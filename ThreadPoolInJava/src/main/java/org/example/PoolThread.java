package org.example;

import java.util.concurrent.BlockingQueue;
// this is the worker thread and states what will each worker node do.
public class PoolThread implements Runnable{

    boolean isStop = false;
    BlockingQueue<Runnable> bq = null;
    PoolThread(BlockingQueue<Runnable> blockingQueue, boolean isStop){
        bq = blockingQueue;
        this.isStop = isStop;
    }
    @Override
    public void run() {
        try {
            while (isStop == false) {
                Runnable runnable = bq.take();
                System.out.println("Thread " + Thread.currentThread().getId() + " is executing task " + runnable.toString());
                runnable.run();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
