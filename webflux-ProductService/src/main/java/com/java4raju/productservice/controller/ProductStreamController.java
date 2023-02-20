package com.java4raju.productservice.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java4raju.productservice.dto.ProductDto;
import com.java4raju.productservice.service.ProductService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("product")
public class ProductStreamController {

    @Autowired
    private Flux<ProductDto> flux;
    
    @Autowired
    private ProductService service;

    @GetMapping(value = "stream/{maxPrice}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getProductUpdates(@PathVariable int maxPrice){
		/*
		  return this.flux .filter(dto -> dto.getPrice() <= maxPrice);
		 */
        return this.service.getAll().delayElements(Duration.ofSeconds(1)).doOnNext(System.out::println);
    }

}
