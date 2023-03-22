
package com.mindhub.golfparadise.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class OrderPurchase {

    @Id
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

    public OrderPurchase(Long id, Double amount, String deliveryAddress, LocalDateTime date) {
        this.id = id;
        this.amount = amount;
        this.deliveryAddress = deliveryAddress;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public void AddProducts( OrderProduct orderProduct){
       orderProduct.setOrderPurchase(this);
        orderProducts.add(orderProduct);
    }
}

