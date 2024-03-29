package com.java4raju.webfluxinitial.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.java4raju.webfluxinitial.dto.MultiplyRequestDto;
import com.java4raju.webfluxinitial.dto.Response;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input){
        return Mono.fromSupplier(() -> input * input)
        		 .delayElement(Duration.ofSeconds(1))
                    .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input){
        return Flux.range(1, 10)
                    .delayElements(Duration.ofSeconds(1))
                    //.doOnNext(i -> SleepUtil.sleepSeconds(1))
                    .doOnNext(i -> System.out.println("reactive-math-service processing : " + i))
                    .map(i -> new Response(i * input));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> dtoMono){
        return dtoMono
                    .map(dto -> dto.getFirst() * dto.getSecond())
                    .delayElement(Duration.ofSeconds(1))
                    .map(Response::new);
    }

}
