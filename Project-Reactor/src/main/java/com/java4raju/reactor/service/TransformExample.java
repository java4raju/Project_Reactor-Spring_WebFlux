package com.java4raju.reactor.service;

import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TransformExample {
  public static void main(String[] args) {
      System.out.println("-- Transforming Flux --");
      Function<Flux<Integer>, Flux<Integer>> transformingFluxFunction =
              flux -> flux.map(i -> i * 2);

           Flux.just(2, 4, 6, 8)
          .transform(transformingFluxFunction)
         // .log()
          .subscribe(System.out::println);
     
      System.out.println("-- Transforming using map --");
      Flux.just(2, 4, 6, 8)
      .map(i -> i*2)
      //.log()
      .subscribe(System.out::println);

      System.out.println("-- Transforming Mono --");
      Function<Mono<String>, Mono<Integer>> transformingMonoFunction =
              mono -> mono.map(s -> s.length());
      Mono.just("supercalifragilisticexpialidocious")
          .transform(transformingMonoFunction)
          .subscribe(System.out::println);
  }
}