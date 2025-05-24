package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPool {
    private int capacity;
    private BlockingQueue<Runnable> queue;
    List<PoolThread> numberOfRunningThreads = null;
    private int maxTasks;
    public ThreadPool(int capacity, int maxTasks) {
        this.capacity = capacity;
        this.maxTasks = maxTasks;
        queue = new ArrayBlockingQueue<>(maxTasks);
        numberOfRunningThreads = new ArrayList<>(capacity);
        for(int i = 0; i < capacity; i++) {
            numberOfRunningThreads.add(new PoolThread(queue));
        }
        for(int i = 0; i < capacity; i++) {
            new Thread(numberOfRunningThreads.get(i)).start();
        }
    }
    public void execute(Runnable task) {
        queue.add(task);
    }

    public void stop() {
        while(!queue.isEmpty()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for(PoolThread p : numberOfRunningThreads){
            p.doStop();
        }

    }

}
