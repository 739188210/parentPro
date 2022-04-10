package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

    private final RestTemplate restTemplate;

    @Autowired
    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/ppppecho", method = RequestMethod.GET)
    public String ehco() {

        return "sdasdds";
        //    return restTemplate.getForObject("http://service-provider/echo/" + str, String.class);
    }

    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    public String ehco(@PathVariable String str) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sdasdds";
    //    return restTemplate.getForObject("http://service-provider/echo/" + str, String.class);
    }

    @RequestMapping(value = "/echosss", method = RequestMethod.POST)
    public String ehcoPost(@RequestBody String str) {

            System.out.println(str);
    return "hello word";
    }

    public static void main(String[] args) {
        String str = "http://sdas";
        String[] result = str.split("/");
        for (String s : result) {
            System.out.println(s);
        }

    }
}
