
package com.mindhub.golfparadise.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class OrderPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Double amount;
    private String deliveryAddress;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy = "orderPurchase", fetch= FetchType.EAGER)
    Set<OrderProduct> orderProducts= new HashSet<>();

    public OrderPurchase() {
    }

    public OrderPurchase( Double amount, String deliveryAddress, LocalDateTime date) {
        this.amount = amount;
        this.deliveryAddress = deliveryAddress;
        this.date = date;
    }

    public void addOrderProduct( OrderProduct orderProduct){
        orderProduct.setOrderPurchase(this);
        orderProducts.add(orderProduct);
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}

