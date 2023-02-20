package com.java4raju.webfluxinital.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.java4raju.webfluxinitial.dto.MultiplyRequestDto;
import com.java4raju.webfluxinitial.dto.Response;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec08AttributesTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void headersTest(){
        Mono<Response> responseMono = this.webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 2))
             //   .attribute("auth", "oauth")
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();

    }

    private MultiplyRequestDto buildRequestDto(int a, int b){
        MultiplyRequestDto dto = new MultiplyRequestDto();
        dto.setFirst(a);
        dto.setSecond(b);
        return dto;
    }

}
