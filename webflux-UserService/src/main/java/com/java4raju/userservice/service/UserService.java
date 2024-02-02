package com.java4raju.userservice.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java4raju.userservice.dto.UserDto;
import com.java4raju.userservice.repository.UserRepository;
import com.java4raju.userservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Flux<UserDto> all(){
        return this.userRepository.findAll()
                    .map(EntityDtoUtil::toDto);
    }
    
    public Flux<UserDto> allStream(){
        return this.userRepository.findAll()
        			.delayElements(Duration.ofMillis(500))
        			.log()
                    .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> getUserById(final int userId){
        return this.userRepository.findById(userId)
                    .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> createUser(Mono<UserDto> userDtoMono){
        return userDtoMono
                    .map(EntityDtoUtil::toEntity)
                    .flatMap(u -> userRepository.save(u))
                    .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> updateUser(int id, Mono<UserDto> userDtoMono){
        return this.userRepository.findById(id)
                .flatMap(u -> userDtoMono
                                .map(EntityDtoUtil::toEntity)
                                .doOnNext(e -> e.setId(id)))
                .flatMap(this.userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteUser(int id){
        return this.userRepository.deleteById(id);
    }

}
