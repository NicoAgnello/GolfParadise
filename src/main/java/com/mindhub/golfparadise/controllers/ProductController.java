package com.mindhub.golfparadise.controllers;

import com.mindhub.golfparadise.dtos.ProductDTO;
import com.mindhub.golfparadise.models.Category;
import com.mindhub.golfparadise.services.ClientService;
import com.mindhub.golfparadise.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ClientService clientService;

    @GetMapping("/products")
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/products/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping("/products")
    public ResponseEntity<Object> addProduct(@RequestParam String name,
                                             @RequestParam String description,
                                             @RequestParam String img,
                                             @RequestParam Double price,
                                             @RequestParam int stock,
                                             @RequestParam Category category) {
        return productService.addProduct(name, description, img, price, stock, category);
    }

    @PatchMapping("/products/stock")
    public ResponseEntity<Object> updateStock(@RequestParam Long id,
                                              @RequestParam int stock) {
        return productService.updateStock(id, stock);
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}


