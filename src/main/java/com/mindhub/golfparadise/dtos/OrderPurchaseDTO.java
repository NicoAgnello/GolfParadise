package com.mindhub.golfparadise.dtos;

import com.mindhub.golfparadise.models.OrderPurchase;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderPurchaseDTO {
    private Long id;
    private Double amount;
    private String deliveryAddress;
    private double deliveryCost;
    private LocalDateTime date;
    Set<OrderProductDTO> orderProducts= new HashSet<>();

    public OrderPurchaseDTO(OrderPurchase orderPurchase) {
        this.id = orderPurchase.getId();
        this.amount = orderPurchase.getAmount();
        this.deliveryAddress = orderPurchase.getDeliveryAddress();
        this.deliveryCost = orderPurchase.getDeliveryCost();
        this.date = orderPurchase.getDate();
        this.orderProducts = orderPurchase.getOrderProducts().stream().map(OrderProductDTO::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Set<OrderProductDTO> getOrderProducts() {
        return orderProducts;
    }
}
