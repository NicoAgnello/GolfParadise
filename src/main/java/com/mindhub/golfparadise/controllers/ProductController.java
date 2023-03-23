package com.mindhub.golfparadise.controllers;

import com.mindhub.golfparadise.dtos.ProductDTO;
import com.mindhub.golfparadise.models.Category;
import com.mindhub.golfparadise.models.Product;
import com.mindhub.golfparadise.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/products/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping("/products")
    public ResponseEntity<Object> addProduct(@RequestParam String name,
                                             @RequestParam String description,
                                             @RequestParam String img,
                                             @RequestParam Double price,
                                             @RequestParam int stock,
                                             @RequestParam Category category) {

        if (name.isEmpty()) {
            return new ResponseEntity<>("Missing name.", HttpStatus.BAD_REQUEST);
        } else if (description.isEmpty()) {
            return new ResponseEntity<>("Missing description.", HttpStatus.BAD_REQUEST);
        } else if (img.isEmpty()) {
            return new ResponseEntity<>("Missing img.", HttpStatus.BAD_REQUEST);
        }

        if (price <= 0.0) {
            return new ResponseEntity<>("Price can't be lower than zero.", HttpStatus.BAD_REQUEST);
        } else if (stock <= 0) {
            return new ResponseEntity<>("Stock can't be lower than zero.", HttpStatus.BAD_REQUEST);
        }

        Product product = new Product(name, description, img, price, stock, category);
        productService.save(product);

        return new ResponseEntity<>("Product added.", HttpStatus.CREATED);
    }

}


