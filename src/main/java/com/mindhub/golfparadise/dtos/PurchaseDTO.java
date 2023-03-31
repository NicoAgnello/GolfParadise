package com.mindhub.golfparadise.dtos;

import java.util.ArrayList;

public class PurchaseDTO {
    private ArrayList<OrderProductDTO> orderProducts = new ArrayList<>();
    private String deliveryAddress;
    private double deliveryCost;

    public PurchaseDTO() {};

    public PurchaseDTO(ArrayList<OrderProductDTO> orderProducts, String deliveryAddress, double deliveryCost) {
        this.orderProducts = orderProducts;
        this.deliveryAddress = deliveryAddress;
        this.deliveryCost = deliveryCost;
    }

    public ArrayList<OrderProductDTO> getOrderProducts() {
        return orderProducts;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }
}