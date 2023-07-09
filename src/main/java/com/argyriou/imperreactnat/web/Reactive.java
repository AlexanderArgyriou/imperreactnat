package com.argyriou.imperreactnat.web;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@RestController
@RequestMapping("reactive")
@Slf4j
public class Reactive {
    static final Random random = new Random();
    @Data
    public static class Item {
        String value;
    }

    @GetMapping(value = "payload", produces = "text/event-stream")
    public Flux<Item> getPayload() {
        return Flux.just(new Item(), new Item(), new Item())
                .delayElements(Duration.of(1, ChronoUnit.SECONDS))
                .doOnNext(item -> {
                    log.info("{} processed item : {}", Thread.currentThread().getName(), item);
                })
                .subscribeOn(Schedulers.parallel())
                .flatMap(this::randomFlatmapOp)
                .log();
    }

    private Flux<Item> randomFlatmapOp(Item item) {
        item.setValue(String.valueOf(random.nextInt()));
        return Flux.just(item)
                .subscribeOn(Schedulers.boundedElastic()).log();
    }
}


