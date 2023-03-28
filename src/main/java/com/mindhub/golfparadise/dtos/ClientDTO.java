package com.mindhub.golfparadise.dtos;

import com.mindhub.golfparadise.models.Client;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    Set<OrderPurchaseDTO> orders = new HashSet<>();

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.password =  client.getPassword();
        this.orders = client.getOrders().stream().map(OrderPurchaseDTO::new).collect(toSet());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<OrderPurchaseDTO> getOrders() {
        return orders;
    }
}
