package org.example;

public class Main {

    public static void main(String[] args) {
        System.out.println("Implementing Thread Pool !");

        ThreadPool threadPool = new ThreadPool(5, 10);
        for(int i = 0 ;i<10;i++){
            int finalI = i;
            threadPool.execute(()->{
                System.out.println("task " + finalI + " is inserted in queue");
            });
        }
        threadPool.stop();
        System.out.println("Done");
        System.exit(0);

    }
}