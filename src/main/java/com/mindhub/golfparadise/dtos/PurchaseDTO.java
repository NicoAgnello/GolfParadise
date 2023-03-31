package com.mindhub.golfparadise.dtos;

import java.util.ArrayList;

public class PurchaseDTO {
    private ArrayList<OrderProductDTO> orderProducts = new ArrayList<>();
    private String deliveryAddress;

    public PurchaseDTO() {};

    public PurchaseDTO(ArrayList<OrderProductDTO> orderProducts, String deliveryAddress) {
        this.orderProducts = orderProducts;
        this.deliveryAddress = deliveryAddress;
    }

    public ArrayList<OrderProductDTO> getOrderProducts() {
        return orderProducts;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}