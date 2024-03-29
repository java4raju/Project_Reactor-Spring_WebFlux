package com.java4raju.webfluxinital.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.java4raju.webfluxinitial.dto.Response;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Example_5_BadRequestTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void badRequestTest(){

        Mono<Response> responseMono = this.webClient
                .get()
                .uri("reactive-math/square/{number}/throw", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseMono)
                .verifyError(WebClientResponseException.BadRequest.class);



    }

}
