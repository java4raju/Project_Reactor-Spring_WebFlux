package com.java4raju.reactor.service;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
public class FluxAndMonoGeneratorService {

    public Flux<String> fluxOfNames() {
      //  return Flux.fromIterable(List.of("peek", "zad","check"))
        //        .log(); // db or a remote service call
        
        return Flux.just("peek", "zad","check").log();
    }

    public Mono<String> nameMono(){
        return Mono.just("peek");
    }

    public Flux<String> namesFlux_map(int stringLength) {
        //filter the string whose length is greater than 3
        return Flux.fromIterable(List.of("peek", "zad","check"))
                .map(String::toUpperCase)
                //.map(s -> s.toUpperCase())
                .filter(s->s.length()> stringLength)
                .map(s-> s.length() + "-"+s)
                .log(); 	// db or a remote service call
    }

    public Flux<String> namesFlux_immutability() {

        var namesFlux = Flux.fromIterable(List.of("peek", "zad","check"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Flux<String> namesFlux_flatmap(int stringLength) {
        //filter the string whose length is greater than 3
        return Flux.fromIterable(List.of("peek", "zad","check"))
                .map(String::toUpperCase)
                //.map(s -> s.toUpperCase())
                .filter(s->s.length()> stringLength)
                .flatMap(s-> splitString(s))  
                .log(); // db or a remote service call
    }

    public Flux<String> namesFlux_flatmap_async(int stringLength) {
        //filter the string whose length is greater than 3
        return Flux.fromIterable(List.of("peek", "zad","check"))
                .map(String::toUpperCase)
                //.map(s -> s.toUpperCase())
                .filter(s->s.length()> stringLength)
                .flatMap(s-> splitString_withDelay(s))  
                .log(); // db or a remote service call
    }

    public Flux<String> namesFlux_concatmap(int stringLength) {
        //filter the string whose length is greater than 3
        return Flux.fromIterable(List.of("peek", "zad","check"))
                .map(String::toUpperCase)
                //.map(s -> s.toUpperCase())
                .filter(s->s.length()> stringLength)
                .concatMap(s-> splitString_withDelay(s))  
                .log(); // db or a remote service call
    }

    public Flux<String> namesFlux_transform(int stringLength) {
        //filter the string whose length is greater than 3

        Function<Flux<String>,Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s->s.length()> stringLength);

        //Flux.empty()
        return Flux.fromIterable(List.of("peek", "zad","check"))
                .transform(filterMap)
                .flatMap(s-> splitString(s))
                .defaultIfEmpty("default")
                .log(); // db or a remote service call
    }

    public Flux<String> namesFlux_transform_switchifEmpty(int stringLength) {
        //filter the string whose length is greater than 3

        Function<Flux<String>,Flux<String>> filterMap = name ->
                name.map(String::toUpperCase)
                .filter(s->s.length()> stringLength)
                .flatMap(s-> splitString(s));

        var defaultFlux = Flux.just("default")
                .transform(filterMap); //"D","E","F","A","U","L","T"

        //Flux.empty()
        return Flux.fromIterable(List.of("peek", "zad","check"))
                .transform(filterMap)
                 // A,L,E,X,C,H,L,O,E
                .switchIfEmpty(defaultFlux)
                .log(); // db or a remote service call
    }

    //ALEX -> FLux(A,L,E,X)
    public Flux<String> splitString(String name){
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> splitString_withDelay(String name){
        var charArray = name.split("");
        // var delay = new Random().nextInt(1000);
        var delay =1000;
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }
    
    
    //Any exception will terminate the reactive stream
    public Flux<String> expect_exception() {
    	return Flux.just("A", "B", "C")
    			   .concatWith(Flux.error(new RuntimeException()))
    			   .concatWith(Flux.just("D")).log();
    }

    //Any exception will not terminate the reactive stream
    public Flux<String> expect_exception_onErrorReturn() {
    	return Flux.just("A", "B", "C")
    			   .concatWith(Flux.error(new RuntimeException()))
    			   .onErrorReturn("D").log();
    			   
    }
    
    //Any exception will not terminate the reactive stream
    public Flux<String> expect_exception_onErrorResume() {
    	return Flux.just("A", "B", "C")
    			   .concatWith(Flux.error(new RuntimeException()))
    			   .onErrorResume(ex -> Flux.just("D", "E", "F")).log();
    }


    

    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.fluxOfNames()
                .subscribe(name -> {
                    System.out.println("Name is : " + name);
                });

        fluxAndMonoGeneratorService.nameMono()
                .subscribe(name->{
                    System.out.println("Mono name is : " + name);
                });

    }
}
