package com.milind.avro.controller;

import com.milind.avro.client.AvroClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

    @Autowired
    AvroClient client;

    @RequestMapping("/test")
    public void test() throws IOException {
        client.sendForConfirmation();
    }
    @RequestMapping("/testzip")
    public void testWithZip() throws IOException {
        client.sendForConfirmationWithZip();
    }

    @RequestMapping("/testjson")
    public void testJSON() throws Exception {
        client.sendForConfirmationWithJSON();
    }

}

