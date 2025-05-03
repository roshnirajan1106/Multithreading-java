package org.example;

public class SharedCounter {
    Integer count = 0;
    public synchronized void increment(){
        for(int i = 0; i < 1000; i++){
            count++;
        }
    }

}
