package org.example;

import java.util.LinkedList;
import java.util.Queue;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer(5);
        Thread producer1 = new Thread(() ->{
            Thread.currentThread().setName("Producer 1");
            while (true){
                buffer.produce((int)(Math.random() * 1000));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + "Interrupted");
                }
            }
        });
        Thread producer2 = new Thread(() ->{
            Thread.currentThread().setName("Producer 2");
            while (true){
                buffer.produce((int)(Math.random() * 1000));
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                   System.out.println(Thread.currentThread().getName() + "Interrupted");
                }
            }
        });
        Thread consumer1 = new Thread(() ->{
            Thread.currentThread().setName("Consumer 1");
            while (true){
                buffer.consume();
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + "Interrupted");
                }
            }
        });
        Thread consumer2 = new Thread(() ->{
            Thread.currentThread().setName("Consumer 2");
            while (true){
                buffer.consume();
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + "Interrupted");
                }
            }
        });
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        try {
            Thread.sleep(3000);
            producer1.interrupt();
            producer2.interrupt();
            consumer1.interrupt();
            consumer2.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}