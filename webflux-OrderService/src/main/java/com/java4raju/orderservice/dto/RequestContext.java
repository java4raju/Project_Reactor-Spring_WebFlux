package com.java4raju.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestContext {

	private ProductDto productDto;
    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

    public RequestContext(PurchaseOrderRequestDto purchaseOrderRequestDto) {
        this.purchaseOrderRequestDto = purchaseOrderRequestDto;
    }
}
