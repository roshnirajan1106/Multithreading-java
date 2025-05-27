package org.example;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Crawler crawler = new Crawler("https://example.com");
        try {
            crawler.crawl();
        }catch (IOException e){
            System.out.println("Exception while trying to get HTTPS");
        }

    }
}