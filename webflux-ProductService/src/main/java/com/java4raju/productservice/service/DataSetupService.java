package com.java4raju.productservice.service;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.java4raju.productservice.dto.ProductDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Sets up data in Mongo-DB
 */
@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private ProductService service;

    @Override
    public void run(String... args) throws Exception {
        ProductDto p1 = new ProductDto("JamesWebTelescope", 1200000000);
        ProductDto p2 = new ProductDto("Curved-TV", 1050);
        ProductDto p3 = new ProductDto("iphone", 123454);
        ProductDto p4 = new ProductDto("headphone", 1200);

        Flux<ProductDto> pFlux = Flux.just(p1, p2, p3, p4)
        		.concatWith(newProducts())
                .flatMap(p -> this.service.insertProduct(Mono.just(p)));
             
        pFlux.subscribe(System.out::println);

    }

    private Flux<ProductDto> newProducts(){
        return Flux.range(1, 500)
                .delayElements(Duration.ofMillis(10))
                .map(i -> new ProductDto("product-" + i, ThreadLocalRandom.current().nextInt(1000, 10000)));
    }

}
