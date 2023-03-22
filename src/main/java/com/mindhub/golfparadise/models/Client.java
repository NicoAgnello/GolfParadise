package com.mindhub.golfparadise.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    Set<OrderPurchase> orders = new HashSet<>();

    public Client() {
    }

    public Client( String first_name, String last_name, String email, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public void addOrders(OrderPurchase orderPurchase){
        orderPurchase.setClient(this);
        orders.add(orderPurchase);
    }

    public Long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<OrderPurchase> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderPurchase> orders) {
        this.orders = orders;
    }

}
