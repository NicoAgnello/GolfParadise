
package com.mindhub.golfparadise.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Integer quantity;
    private Double totalAmount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="order_purchase_id")
    private OrderPurchase orderPurchase;

    public OrderProduct() {
    }

    public OrderProduct( Integer quantity, Double totalAmount) {
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
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

