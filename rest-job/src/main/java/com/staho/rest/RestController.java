package com.staho.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicInteger;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final AtomicInteger counter = new AtomicInteger();

    @RequestMapping("/enhance")
    public Client enhanceClient(@RequestParam(value="uuid") String uuid) {
        Client cl = new Client();

        cl.setFirstName("Kamil");
        cl.setName("Stachowicz" + counter.incrementAndGet());
        if(counter.get() % 4 == 0){
            cl.setUuid("0700");
        }
        else {
            cl.setUuid(uuid);
        }
        return cl;
    }
}
