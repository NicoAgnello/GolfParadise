package com.mindhub.golfparadise.services;

import com.mindhub.golfparadise.dtos.ProductDTO;
import com.mindhub.golfparadise.models.Category;
import com.mindhub.golfparadise.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getProducts();

    Product findById(Long id);

    ProductDTO getProduct(Long id);

    void save(Product product);

    public ResponseEntity<Object> addProduct(@RequestParam String name,
                                             @RequestParam String description,
                                             @RequestParam String img,
                                             @RequestParam Double price,
                                             @RequestParam int stock,
                                             @RequestParam Category category);

    public ResponseEntity<Object> updateStock(@RequestParam Long id,
                                              @RequestParam int stock);

    public ResponseEntity<Object> deleteProduct(@PathVariable Long id);
}
