package com.mindhub.golfparadise.dtos;

import java.util.ArrayList;

public class PurchaseDTO {
    private ArrayList<OrderProductDTO> orderProducts = new ArrayList<>();

    public PurchaseDTO() {};

    public PurchaseDTO(ArrayList<OrderProductDTO> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public ArrayList<OrderProductDTO> getOrderProducts() {
        return orderProducts;
    }

}

