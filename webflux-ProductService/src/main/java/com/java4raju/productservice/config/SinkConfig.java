package com.java4raju.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.java4raju.productservice.dto.ProductDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

    @Bean
    public Sinks.Many<ProductDto> sink(){
        return Sinks.many().replay().limit(1);
    }

    @Bean
    public Flux<ProductDto> productBroadcast(Sinks.Many<ProductDto> sink){
        return sink.asFlux();
    }

}
