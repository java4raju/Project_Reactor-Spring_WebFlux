package com.java4raju.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderRequestDto {

    private Integer userId;
    private String productId;

}
