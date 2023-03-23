package com.mindhub.golfparadise.dtos;

import com.mindhub.golfparadise.models.OrderProduct;

public class OrderProductDTO {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double totalAmount;


    public OrderProductDTO(OrderProduct orderProduct) {
        id = orderProduct.getId();
        productId = orderProduct.getId();
        productName = orderProduct.getProduct().getName();
        quantity = orderProduct.getQuantity();
        totalAmount = orderProduct.getTotalAmount();
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}
