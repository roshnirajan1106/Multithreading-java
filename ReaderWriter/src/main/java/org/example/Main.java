package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ReaderWriter readerWriter = new ReaderWriter();
        Thread read1 = new Thread(() -> {
            Thread.currentThread().setName("Read-1");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            readerWriter.read();
        });
        Thread read2 = new Thread(() -> {
            Thread.currentThread().setName("Read-2");
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            readerWriter.read();
        });
        Thread write1 = new Thread(() -> {
            Thread.currentThread().setName("Write-1");
            readerWriter.write((int) (Math.random() * 1000));

        });
        Thread write2 = new Thread(() -> {
            Thread.currentThread().setName("Write-2");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            readerWriter.write((int) (Math.random() * 1000));
        });

        read1.start();
        read2.start();
        write1.start();
        write2.start();

    }
}