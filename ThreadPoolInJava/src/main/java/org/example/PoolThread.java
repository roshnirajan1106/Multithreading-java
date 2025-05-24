package org.example;

import java.util.concurrent.BlockingQueue;
// this is the worker thread and states what will each worker node do.
public class PoolThread implements Runnable{

    boolean isStop = false;
    Thread thread = null;
    BlockingQueue<Runnable> bq = null;
    PoolThread(BlockingQueue<Runnable> blockingQueue){
        bq = blockingQueue;

    }
    @Override
    public void run() {
        thread = Thread.currentThread();
            while (!isStop) {
                try {
                    Runnable runnable = bq.take(); //blocking operation. will be stuck here until queue is not empty.
                    System.out.println("Thread " + Thread.currentThread().getId() + " is executing task " + runnable.toString());
                    runnable.run();
                } catch (InterruptedException e) {
                    System.out.println("Thread is interrupted");
                }
            }
    }
    public void doStop(){
        isStop = true;
        this.thread.interrupt();
    }
}
