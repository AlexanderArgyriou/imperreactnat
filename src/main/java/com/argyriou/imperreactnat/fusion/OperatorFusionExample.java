package com.argyriou.imperreactnat.fusion;

import reactor.core.publisher.Flux;

public class OperatorFusionExample {
    public static void main(String[] args) {
        Flux<Integer> source = Flux.range(1, 10);

        Flux<Integer> result = source
                .map(i -> i * 2)
                .filter(i -> i % 3 == 0)
                .flatMap(i -> Flux.just(i, i + 1))
                .log();

        result.subscribe(System.out::println);
    }
}
