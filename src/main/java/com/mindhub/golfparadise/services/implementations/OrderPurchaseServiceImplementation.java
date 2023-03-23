package com.mindhub.golfparadise.services.implementations;

import com.mindhub.golfparadise.dtos.OrderPurchaseDTO;
import com.mindhub.golfparadise.dtos.ProductDTO;
import com.mindhub.golfparadise.models.OrderPurchase;
import com.mindhub.golfparadise.repositories.OrderRepository;
import com.mindhub.golfparadise.services.OrderPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderPurchaseServiceImplementation implements OrderPurchaseService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<OrderPurchaseDTO> getOrderPurchases() {
        return orderRepository.findAll().stream().map(OrderPurchaseDTO::new).collect(Collectors.toList());
    }

    @Override
    public void save(OrderPurchase orderPurchase) {

    }
}
