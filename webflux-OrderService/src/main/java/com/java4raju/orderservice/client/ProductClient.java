package com.java4raju.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.java4raju.orderservice.dto.ProductDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@Value("${product.service.url}") String url){
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    /**
     * Call Product Service
     * @param productId
     * @return Mono<ProductDto>
     */
    public Mono<ProductDto> getProductById(final String productId){
        return this.webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Flux<ProductDto> getAllProducts(){
        return this.webClient
                    .get()
                    .uri("all")
                    .retrieve()
                    .bodyToFlux(ProductDto.class);
    }

}
