package org.example;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedBuffer{
    public Queue<Integer> queue = new LinkedList<>();
    Lock lock = new ReentrantLock();
    private int capacity;
    //meaning of notFull condition - notFull.await means wait till the queue is not full
    //meaning of notEmpty condition - notEmpty.await means wait till the queue is not empty
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public  SharedBuffer(int capacity) {
        this.capacity = capacity;
    }

    public void produce(int random) {
        lock.lock();
        try{
            while(queue.size() >= capacity){
                System.out.println("The queue is full please wait!");
                notFull.await();
            }
            System.out.println("Produced by : " + Thread.currentThread().getName() + " : " + random);
            queue.add(random);
            notEmpty.signal();
        } catch (InterruptedException e) {
           lock.unlock();
        }

    }
    public void consume(){
        lock.lock();
        try {
            while (queue.isEmpty()) {
                System.out.println("Nothing to consume!");
                notEmpty.await();
            }
            int num = queue.poll();
            System.out.println("Consumed by : " + Thread.currentThread().getName() + " : " + num);
            notFull.signal();
        }catch (InterruptedException e) {
           lock.unlock();
        }
    }
}
