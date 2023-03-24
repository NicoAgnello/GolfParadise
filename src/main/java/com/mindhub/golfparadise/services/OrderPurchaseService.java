package com.mindhub.golfparadise.services;

import com.mindhub.golfparadise.dtos.OrderPurchaseDTO;
import com.mindhub.golfparadise.models.OrderPurchase;

import java.util.List;

public interface OrderPurchaseService {

    OrderPurchaseDTO getOrderPurchase(Long id);

    List<OrderPurchaseDTO> getOrderPurchases();

    void save(OrderPurchase orderPurchase);
}
