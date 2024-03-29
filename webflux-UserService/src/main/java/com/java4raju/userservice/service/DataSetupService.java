package com.java4raju.userservice.service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.java4raju.userservice.dto.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {

    @Value("classpath:h2/init.sql")
    private Resource initSql;

    @Autowired
    private R2dbcEntityTemplate entityTemplate;
    
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);
        System.out.println(query);
        this.entityTemplate
                .getDatabaseClient()
                .sql(query)
                .then()
                .subscribe();
        
        Flux.just(new UserDto("Rahul",  ThreadLocalRandom.current().nextInt(5000, 10000)))
        	.concatWith(newUsers())
        	.flatMap(u -> userService.createUser(Mono.just(u)))
        	//.delayElements(Duration.ofMillis(50))
        	.subscribe(System.out::println);
        	
    }
    
    private Flux<UserDto> newUsers(){
    	return Flux.range(1, 500)
    			 .delayElements(Duration.ofMillis(10))
    	           .map(i -> new UserDto("User-"+i,  ThreadLocalRandom.current().nextInt(5000, 10000) ));
    }
}
