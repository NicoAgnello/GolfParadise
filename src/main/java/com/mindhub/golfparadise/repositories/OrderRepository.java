package com.mindhub.golfparadise.repositories;

import com.mindhub.golfparadise.models.OrderPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository <OrderPurchase, Long>{
}
