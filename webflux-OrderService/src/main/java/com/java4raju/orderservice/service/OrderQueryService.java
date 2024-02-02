package com.java4raju.orderservice.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java4raju.orderservice.dto.PurchaseOrderResponseDto;
import com.java4raju.orderservice.repository.PurchaseOrderRepository;
import com.java4raju.orderservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

    @Autowired
    private PurchaseOrderRepository orderRepository;

    /**
     * Returns Flux of transaction for the user
     * @param userId
     * @return Flux<PurchaseOrderResponseDto>
     */
    /*public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId){
        return Flux.fromStream(() -> this.orderRepository.findByUserId(userId).stream()) // blocking
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }*/
    
    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId){
        return this.orderRepository.findByUserId(userId)
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }
    
    /**
     * Find all transaction
     * @return Flux<PurchaseOrderResponseDto>
     */
    public Flux<PurchaseOrderResponseDto> getAllTransaction(){
    	return this.orderRepository.findAll()
    			.delayElements(Duration.ofMillis(500))
    			.map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

}
