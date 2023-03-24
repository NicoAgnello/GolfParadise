package com.mindhub.golfparadise.services;

import com.mindhub.golfparadise.dtos.OrderProductDTO;
import com.mindhub.golfparadise.models.OrderProduct;

import java.util.List;

public interface OrderProductService {

    OrderProductDTO getOrderProduct(Long id);

    List<OrderProductDTO> getOrderProducts();

    void save(OrderProduct orderProduct);
}
