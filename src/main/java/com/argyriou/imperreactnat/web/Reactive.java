package com.argyriou.imperreactnat.web;

import com.argyriou.imperreactnat.payload.Item;
import com.argyriou.imperreactnat.payload.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("reactive")
public class Reactive {
    @GetMapping(value = "payload", produces = "text/event-stream")
    public Flux<Item> getPayload(@RequestHeader Map<String, String> headers) {
        Thread warriorThread = Thread.currentThread();

//        System.out.printf("Thread handles request with id : %s -> %s%n",
//                headers.get("id"), warriorThread.getName());

        Flux<Item> items = new Payload(headers.get("id"))
                .getItems(warriorThread);

//        System.out.printf("Thread %s -> says : I am going to handle my next request bb :D%n",
//                warriorThread.getName());

        return items;
    }
}


