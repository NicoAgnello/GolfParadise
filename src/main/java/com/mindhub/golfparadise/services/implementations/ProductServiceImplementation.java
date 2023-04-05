package com.mindhub.golfparadise.services.implementations;

import com.mindhub.golfparadise.dtos.ProductDTO;
import com.mindhub.golfparadise.models.Category;
import com.mindhub.golfparadise.models.Product;
import com.mindhub.golfparadise.repositories.ProductRepository;
import com.mindhub.golfparadise.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ProductDTO getProduct(Long id) {
        return productRepository.findById(id).map(ProductDTO::new).orElse(null);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public ResponseEntity<Object> addProduct(String name, String description, String img, Double price, int stock, Category category) {
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
        productRepository.save(product);

        return new ResponseEntity<>("Product added.", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> updateStock(Long id, int stock) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            if (stock < 0) {
                return new ResponseEntity<>("Stock can't be lower than 0.", HttpStatus.BAD_REQUEST);
            }
            product.setStock(stock);
            productRepository.save(product);
            return new ResponseEntity<>("Stock updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null && product.getStock() == 0 && product.isActive()) {
            product.setActive(false);
            productRepository.save(product);
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
