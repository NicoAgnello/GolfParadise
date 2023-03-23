package com.mindhub.golfparadise.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;
    private String name;
    private String description;
    private String img;
    private Double price;
    private int stock;
    private Category category;
    private boolean active;
    @OneToMany(mappedBy = "product", fetch= FetchType.EAGER)
    Set<OrderProduct> orderProducts= new HashSet<>();

    public Product() {
    }

    public Product( String name, String description, String img, Double price, int stock, Category category) {
        this.name = name;
        this.description = description;
        this.img = img;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.active = true;
    }

    public void addOrderProduct(OrderProduct orderProduct){
        orderProduct.setProduct(this);
        orderProducts.add(orderProduct);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
