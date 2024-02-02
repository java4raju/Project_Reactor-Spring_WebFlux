package com.java4raju.orderservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.java4raju.orderservice.entity.PurchaseOrder;

import reactor.core.publisher.Flux;

/**
 * Blocking API
 */
/*@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    List<PurchaseOrder> findByUserId(int userId);

}*/

@Repository
public interface PurchaseOrderRepository extends ReactiveCrudRepository<PurchaseOrder, Integer> {

    Flux<PurchaseOrder> findByUserId(int userId);

}

