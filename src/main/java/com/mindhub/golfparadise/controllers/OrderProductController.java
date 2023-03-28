package com.mindhub.golfparadise.controllers;

import com.mindhub.golfparadise.dtos.OrderProductDTO;
import com.mindhub.golfparadise.services.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderProductController {
    @Autowired
    OrderProductService orderProductService;

    @GetMapping("/order-products")
    public List<OrderProductDTO> getOrderProducts() {
        return orderProductService.getOrderProducts();
    }
}
