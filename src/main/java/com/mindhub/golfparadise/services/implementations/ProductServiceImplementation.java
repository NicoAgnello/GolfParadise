package com.mindhub.golfparadise.services.implementations;

import com.mindhub.golfparadise.dtos.ProductDTO;
import com.mindhub.golfparadise.repositories.ProductRepository;
import com.mindhub.golfparadise.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
