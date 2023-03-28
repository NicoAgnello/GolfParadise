package com.mindhub.golfparadise.dtos;

import com.mindhub.golfparadise.models.Category;
import com.mindhub.golfparadise.models.OrderProduct;
import com.mindhub.golfparadise.models.Product;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private String img;
    private Double price;
    private int stock;
    private Category category;
    private boolean active;
//    Set<OrderProduct> orderProducts= new HashSet<>();

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.img = product.getImg();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.category = product.getCategory();
        this.active = product.isActive();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }

    public Double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isActive() {
        return active;
    }

    //    public Set<OrderProduct> getOrderProducts() {
//        return orderProducts;
//    }
}
