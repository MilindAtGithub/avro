package com.milind.avro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
    }
}
