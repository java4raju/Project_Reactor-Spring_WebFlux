package com.java4raju.reactor.service;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService =
            new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.fluxOfNames();

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("peek", "zad","check")
                //.expectNextCount(3)
                .expectNext("peek")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesFlux_map() {

        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map(stringLength);

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("ALEX", "BEN", "CHLOE")
                .expectNext("4-ALEX","5-CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFlux_immutability() {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_immutability();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("peek", "zad","check")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap() {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap(stringLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap_async() {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(stringLength);

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFlux_concatmap() {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_concatmap(stringLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                //.expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFlux_transform() {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_1() {
        //given
        int stringLength = 6;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_switchifEmpty() {
        //given
        int stringLength = 6;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform_switchifEmpty(stringLength);

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();
    }
    
  //Exceptions Exception______________________________________________
    
    @Test
    void test_ExpectException() {
    	Flux<String> flux = fluxAndMonoGeneratorService.expect_exception();
    	
    	StepVerifier.create(flux)
    				.expectNext("A", "B", "C")
    				.expectError(RuntimeException.class)
    				.verify();
      }
    
    
    @Test
    void test_Expect_exception_onErrorReturn(){
    	Flux<String> flux = fluxAndMonoGeneratorService.expect_exception_onErrorReturn();
    	
    	StepVerifier.create(flux)
    				.expectNext("A", "B", "C", "D")
    				.verifyComplete();
    }
    
    
    @Test
    void test_Expect_exception_onErrorResume(){
    	Flux<String> flux = fluxAndMonoGeneratorService.expect_exception_onErrorResume();
    	
    	StepVerifier.create(flux)
    				.expectNext("A","B","C","D","E","F")
    				.verifyComplete();
    }
  
    
}
