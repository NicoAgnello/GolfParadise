package com.mindhub.golfparadise.services.implementations;

import com.mindhub.golfparadise.dtos.OrderProductDTO;
import com.mindhub.golfparadise.models.OrderProduct;
import com.mindhub.golfparadise.repositories.OrderProductRepository;
import com.mindhub.golfparadise.services.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderProductImplementation implements OrderProductService {
    @Autowired
    OrderProductRepository orderProductRepository;

    @Override
    public List<OrderProductDTO> getOrderProducts() {
        return orderProductRepository.findAll().stream().map(OrderProductDTO::new).collect(Collectors.toList());
    }

    @Override
    public void save(OrderProduct orderProduct) {
        orderProductRepository.save(orderProduct);
    }
}
