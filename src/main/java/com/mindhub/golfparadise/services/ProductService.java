package com.mindhub.golfparadise.services;

import com.mindhub.golfparadise.dtos.ProductDTO;
import com.mindhub.golfparadise.models.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getProducts();

    ProductDTO findById(Long id);

    void save(Product product);
}
