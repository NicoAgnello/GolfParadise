
package com.mindhub.golfparadise.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class OrderProduct {


    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
    private Double totalAmount;




    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="order_Purchase_id")
    private OrderPurchase orderPurchase;
    public OrderProduct() {
    }


    public OrderProduct(Long id, OrderPurchase orderPurchase, Product product, Integer quantity, Double totalAmount) {
        this.id = id;
        this.orderPurchase = orderPurchase;
        this.product = product;
        this.quantity = quantity;
        this.totalAmount = totalAmount;


    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderPurchase getOrderPurchase() {
        return orderPurchase;
    }

    public void setOrderPurchase(OrderPurchase orderPurchase) {
        this.orderPurchase = orderPurchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }


}

