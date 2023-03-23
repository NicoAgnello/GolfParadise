package com.mindhub.golfparadise.controllers;

import com.mindhub.golfparadise.dtos.ProductDTO;
import com.mindhub.golfparadise.models.Category;
import com.mindhub.golfparadise.models.Product;
import com.mindhub.golfparadise.services.ClientService;
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

    @PatchMapping("/products/stock")
    public ResponseEntity<Object> updateStock(@RequestParam Long id,
                                              @RequestParam int stock) {

        Product product = productService.findById(id);
        if (product != null) {
            if (stock < 0) {
                return new ResponseEntity<>("Stock can't be lower than 0.", HttpStatus.BAD_REQUEST);
            }
            product.setStock(stock);
            productService.save(product);
            return new ResponseEntity<>("Stock updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found.", HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {

        Product product = productService.findById(id);
        if (product != null && product.getStock() == 0 && product.isActive()) {
            product.setActive(false);
            productService.save(product);
            return new ResponseEntity<>("Product deleted", HttpStatus.OK);
        } else if (product == null){
            return new ResponseEntity<>("Product not found.", HttpStatus.BAD_REQUEST);
        } else if (!product.isActive()) {
            return new ResponseEntity<>("Product is already inactive", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Can't delete products with stock higher than 1.", HttpStatus.BAD_REQUEST);
        }

    }

}


