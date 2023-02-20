package com.java4raju.webfluxinitial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.java4raju.webfluxinitial.dto.MultiplyRequestDto;
import com.java4raju.webfluxinitial.dto.Response;
import com.java4raju.webfluxinitial.service.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {

    @Autowired
    private ReactiveMathService mathService;

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input){
        return this.mathService.findSquare(input)
                        .defaultIfEmpty(new Response(-1));
    }

    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input){
        return this.mathService.multiplicationTable(input);
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return this.mathService.multiplicationTable(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono,
                                   @RequestHeader Map<String, String> headers){
        System.out.println(headers);
        return this.mathService.multiply(requestDtoMono);
    }


}
