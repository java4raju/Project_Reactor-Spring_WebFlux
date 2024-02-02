package com.java4raju.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.java4raju.orderservice.dto.PurchaseOrderRequestDto;
import com.java4raju.orderservice.dto.PurchaseOrderResponseDto;
import com.java4raju.orderservice.service.OrderFulfillmentService;
import com.java4raju.orderservice.service.OrderQueryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private OrderQueryService queryService;

    /**
     * Accepts order request
     * @param requestDtoMono
     * @return Mono<ResponseEntity<PurchaseOrderResponseDto>>
     */
    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDto>> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono){
        return this.orderFulfillmentService.processOrder(requestDtoMono)
                                .map(ResponseEntity::ok)
                                .onErrorReturn(WebClientRequestException.class, ResponseEntity.badRequest().build())
                                .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    /**
     * Returns all the transaction history of user
     * @param userId
     * @return Flux<PurchaseOrderResponseDto>
     */
    @GetMapping("user/{userId}")
    public Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable int userId){
        return this.queryService.getProductsByUserId(userId);
    }
    
    /**
     * 
     * @return Flux<PurchaseOrderResponseDto> 
     */
    @GetMapping(value = "all/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE )
    public Flux<PurchaseOrderResponseDto> findAllTnx() {
        return this.queryService.getAllTransaction();
    }

}
