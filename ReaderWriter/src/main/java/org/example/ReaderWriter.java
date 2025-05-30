package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReaderWriter {
    int sharedValue = 0;
    int readerCount =0;
    int newValue = 0;
    int writerCount = 0;
    boolean isWriting = false;
    Lock lock = new ReentrantLock(true);
    Condition condition = lock.newCondition();

    public void read() {
        lock.lock();

        try{
            while(isWriting && writerCount >0){
                condition.await();
            }
            readerCount ++;
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
        //reading done
        System.out.println("Read by " + Thread.currentThread().getName() + " - " + sharedValue);
        lock.lock();
        readerCount--;
        if(readerCount == 0){
            condition.signalAll();
        }
        lock.unlock();
    }
    public void write(int value){
        lock.lock();
        try{
            writerCount ++;
            while(isWriting ){
                condition.await();
            }
            isWriting = true;
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
        System.out.println("Written by " + Thread.currentThread().getName() + " - " + value);
        sharedValue = value;
        lock.lock();
        isWriting = false;
        writerCount--;
        condition.signalAll();
        lock.unlock();
    }
}
