package com.java4raju.webfluxinital.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.java4raju.webfluxinitial.dto.Response;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Example_2_GetFluxTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void fluxTest(){

        Flux<Response> responseFlux = this.webClient
                .get()
                .uri("reactive-math/table/{number}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();

    }

    @Test
    public void fluxStreamTest(){
        Flux<Response> responseFlux = this.webClient
                .get()
                .uri("reactive-math/table/{number}/stream", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(100)
                .verifyComplete();

    }

}
