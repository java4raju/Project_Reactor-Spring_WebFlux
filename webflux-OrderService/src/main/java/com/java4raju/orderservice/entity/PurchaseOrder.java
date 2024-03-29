package com.java4raju.orderservice.entity;

import com.java4raju.orderservice.dto.OrderStatus;

/*import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;*/
import lombok.Data;
import lombok.ToString;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


/*
@Data
@Entity
@ToString
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private Integer id;
    private String productId;
    private Integer userId;
    private Integer amount;
    private OrderStatus status;

}*/

@Data
@Table
@ToString
public class PurchaseOrder {

    @Id
    private Integer id;
    private String productId;
    private Integer userId;
    private Integer amount;
    private OrderStatus status;

}
