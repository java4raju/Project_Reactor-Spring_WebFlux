package com.java4raju.webfluxinital.webtestclient;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.java4raju.webfluxinitial.controller.ReactiveMathController;
import com.java4raju.webfluxinitial.dto.MultiplyRequestDto;
import com.java4raju.webfluxinitial.dto.Response;
import com.java4raju.webfluxinitial.service.ReactiveMathService;

import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathController.class)
public class Example_2_ControllerPostTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService reactiveMathService;

    @Test
    public void postTest(){
        Mockito.when(reactiveMathService.multiply(Mockito.any())).thenReturn(Mono.just(new Response(1)));

        this.client
                .post()
                .uri("/reactive-math/multiply")
                .accept(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBasicAuth("username", "password"))
                .headers(h -> h.set("somekey", "somevalue"))
                .bodyValue(new MultiplyRequestDto())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }


}
