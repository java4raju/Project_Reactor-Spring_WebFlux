package com.java4raju.orderservice.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java4raju.orderservice.client.ProductClient;
import com.java4raju.orderservice.client.UserClient;
import com.java4raju.orderservice.dto.PurchaseOrderRequestDto;
import com.java4raju.orderservice.dto.PurchaseOrderResponseDto;
import com.java4raju.orderservice.dto.RequestContext;
import com.java4raju.orderservice.repository.PurchaseOrderRepository;
import com.java4raju.orderservice.util.EntityDtoUtil;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

@Service
public class OrderFulfillmentService {

    @Autowired
    private PurchaseOrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    /**
     * 
     * Steps:-
     * 1.converts the request object to entity object
     * 2.calls the product API
     * 3. converts the response to dto
     * 4. calls the user API
     * 5. create the purchase order status
     * 6. save the transaction in order service DB
     * 7. convert response to purchase DTO
     * 8. subscribe the response to emit MONO
     * 
     * @param requestDtoMono
     * @return Mono<PurchaseOrderResponseDto>
     */
    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono){
        return requestDtoMono.map(RequestContext::new)
        		.flatMap(this::productServiceResponse)
        		.doOnNext(EntityDtoUtil::setTransactionRequestDto)  
        		.flatMap(this::userServiceResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .flatMap(this.orderRepository::save)
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Steps:-
     * 1. With Product Web Client calls get Product API
     * 2. Converts the response into product DTO
     * 3. If product API fails to respond, retries with 5 attempts at interval of 1 seconds
     * @param rc
     * @return Mono<RequestContext>
     */
    private Mono<RequestContext> productServiceResponse(RequestContext rc){
        return this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(rc::setProductDto)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(rc);
    }

    /**
     * Steps:-
     * 1. With Product Web Client calls get Product API
     * 2. Converts the response into setTransactionResponseDto
     * @param rc
     * @return
     */
    private Mono<RequestContext> userServiceResponse(RequestContext rc){
        return this.userClient.initiateTransaction(rc.getTransactionRequestDto())
                .doOnNext(rc::setTransactionResponseDto)
                .thenReturn(rc);
    }

}
