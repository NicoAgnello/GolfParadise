package com.mindhub.golfparadise.repositories;

import com.mindhub.golfparadise.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository <OrderProduct,Long> {

}
