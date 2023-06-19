package com.argyriou.imperreactnat.payload;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public class Payload {
    private static final int PAYLOAD_SIZE = 3;
    private final Item[] items = new Item[PAYLOAD_SIZE];

    public Payload(String reqId) {
        for (int i = 0; i < PAYLOAD_SIZE; ++i) {
            items[i] = new Item();
            items[i].setInfo(String.format("request id : %s", reqId));
        }
    }

    public Flux<Item> getItems(Thread warrior) {
        return Flux.fromArray(items)
                .map(Item::new)
                .delayElements(Duration.of(1, ChronoUnit.SECONDS))
                .doOnNext(item -> {
                    item.setInfo(String.format("%s processed by thread : %s%n" +
                                    "Warrior thread that handled the request has state:  %s",
                            item.getInfo(),
                            Thread.currentThread().getName(),
                            warrior.getState()));
                })
                .subscribeOn(Schedulers.parallel())
                .flatMap(this::randomFlatmapOp)
                .log();
    }

    public Item[] balloon() {
        return Stream.generate(Item::new)
                .limit(10)
                .toArray(Item[]::new);
    }

    public Flux<Item> gardenHose() {
        return Flux.fromStream(
                Stream.generate(Item::new)
                .limit(10)
        ).delayElements(Duration
                .of(1, ChronoUnit.SECONDS));
    }

    private Flux<Item> randomFlatmapOp(Item original) {
        Item item = new Item();
        item.setInfo(original.getInfo());
        return Flux.just(item)
                .subscribeOn(Schedulers.boundedElastic()).log();
    }
}
