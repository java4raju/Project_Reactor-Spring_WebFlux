package com.java4raju.productservice.service;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.java4raju.productservice.dto.ProductDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private ProductService service;

    @Override
    public void run(String... args) throws Exception {
        ProductDto p1 = new ProductDto("4k-tv", 1000);
        ProductDto p2 = new ProductDto("slr-camera", 750);
        ProductDto p3 = new ProductDto("iphone", 800);
        ProductDto p4 = new ProductDto("headphone", 100);

        Flux<ProductDto> pFlux = Flux.just(p1, p2, p3, p4)
        		.concatWith(newProducts())
                .flatMap(p -> this.service.insertProduct(Mono.just(p)));
             
        pFlux.subscribe(System.out::println);

    }

    private Flux<ProductDto> newProducts(){
        return Flux.range(1, 40)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> new ProductDto("product-" + i, ThreadLocalRandom.current().nextInt(10, 100)));
    }

}
