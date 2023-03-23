package com.mindhub.golfparadise.controllers;

import com.mindhub.golfparadise.dtos.OrderPurchaseDTO;
import com.mindhub.golfparadise.dtos.ProductDTO;
import com.mindhub.golfparadise.repositories.OrderRepository;
import com.mindhub.golfparadise.services.OrderPurchaseService;
import com.mindhub.golfparadise.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderPurchaseController {

    @Autowired
    OrderPurchaseService orderPurchaseService;

    @GetMapping("/orderPurchases")
    public List<OrderPurchaseDTO> getOrderPurchases() {
        return orderPurchaseService.getOrderPurchases();
    }


}
