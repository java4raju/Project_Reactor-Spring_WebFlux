package com.java4raju.productservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;

import com.java4raju.productservice.dto.ProductDto;
import com.java4raju.productservice.repository.ProductRepository;
import com.java4raju.productservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private Sinks.Many<ProductDto> sink;

    /**
     * Get all the products
     * Converts to DTO
     * @return Flux<ProductDto>
     */
    public Flux<ProductDto> getAll(){
        return this.repository.findAll()
                    .map(EntityDtoUtil::toDto);
    }

    public Flux<ProductDto> getProductByPriceRange(int min, int max){
        return this.repository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id){
        return this.repository.findById(id)
                             .map(EntityDtoUtil::toDto);
    }

    /**
     * Steps:-
     * 1. Converts DTO to entity
     * 2. saves product to DB
     * 3. Converts entity to DTO
     * 4. emit elements
     * @param productDtoMono
     * @return Mono<ProductDto>
     */
    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.repository::insert)
                .map(EntityDtoUtil::toDto)
               	.doOnNext(this.sink::tryEmitNext);
    }

    /**
     * Steps:-
     * 1. Finds user By Id, which return Mono of product entity
     * 2. Sets Id to the entity
     * 3. Saves the entity to DB
     * Maps to DTO to return response
     * 
     * @param id
     * @param productDtoMono
     * @return Mono<ProductDto>
     */
    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono){
       return this.repository.findById(id)
                            .flatMap(p -> productDtoMono
                                            .map(EntityDtoUtil::toEntity)
                                            .doOnNext(e -> e.setId(id)))
                            .flatMap(this.repository::save)
                            .map(EntityDtoUtil::toDto);
    }

    /**
     * @param id
     * @return Mono<Void>
     */
    public Mono<Void> deleteProduct(String id){
        return this.repository.deleteById(id);
    }

}
