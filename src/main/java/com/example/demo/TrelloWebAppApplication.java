package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class TrelloWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrelloWebAppApplication.class, args);
    }
}
