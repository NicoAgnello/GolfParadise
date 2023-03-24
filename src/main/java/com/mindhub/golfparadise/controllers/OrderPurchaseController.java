package com.mindhub.golfparadise.controllers;

import com.mindhub.golfparadise.dtos.OrderProductDTO;
import com.mindhub.golfparadise.dtos.OrderPurchaseDTO;
import com.mindhub.golfparadise.dtos.PurchaseDTO;
import com.mindhub.golfparadise.models.Client;
import com.mindhub.golfparadise.models.OrderProduct;
import com.mindhub.golfparadise.models.OrderPurchase;
import com.mindhub.golfparadise.models.Product;
import com.mindhub.golfparadise.services.ClientService;
import com.mindhub.golfparadise.services.OrderProductService;
import com.mindhub.golfparadise.services.OrderPurchaseService;
import com.mindhub.golfparadise.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderPurchaseController {

    @Autowired
    OrderPurchaseService orderPurchaseService;
    @Autowired
    OrderProductService orderProductService;
    @Autowired
    ClientService clientService;
    @Autowired
    ProductService productService;

    @GetMapping("/orderPurchases")
    public List<OrderPurchaseDTO> getOrderPurchases() {
        return orderPurchaseService.getOrderPurchases();
    }

    @Transactional
    @PostMapping("/orderProducts/generate")
    public ResponseEntity<Object> generateOrderProduct(Authentication authentication,
                                                       @RequestBody PurchaseDTO purchaseDTO) {

        Client client = clientService.findByEmail(authentication.getName());
        ArrayList<OrderProductDTO> orderProducts = purchaseDTO.getOrderProducts();
        ArrayList<Double> finalAmount = new ArrayList<>();
        boolean validOrderProduct = true;
        boolean enoughStock = true;
        for (OrderProductDTO orderProductDTO : orderProducts) {
            Product product = productService.findById(orderProductDTO.getProductId());
            if (orderProductDTO.getQuantity() < 1) {
                return new ResponseEntity<>("Order quantity can't be lower than 1.", HttpStatus.BAD_REQUEST);
            }
            if (product == null) {
                validOrderProduct = false;
                break;
            }  else {
                if (product.getStock() < orderProductDTO.getQuantity()) {
                    enoughStock = false;
                    break;
                }
            }
        }

        if (!validOrderProduct) {
            return new ResponseEntity<>("Invalid product id", HttpStatus.BAD_REQUEST);
        } else if (!enoughStock) {
            return new ResponseEntity<>("Not enough stock.", HttpStatus.BAD_REQUEST);
        }

        OrderPurchase orderPurchase = new OrderPurchase(0.0, "Delivery Address", LocalDateTime.now());
        orderPurchaseService.save(orderPurchase);

        orderProducts.forEach(orderProductDTO -> {
            Product product = productService.findById(orderProductDTO.getProductId());
            product.setStock(product.getStock() - orderProductDTO.getQuantity());
            productService.save(product);
            double totalAmount = orderProductDTO.getQuantity() * product.getPrice();
            finalAmount.add(totalAmount);
            OrderProduct orderProduct = new OrderProduct(orderProductDTO.getQuantity(), totalAmount);
            orderPurchase.addOrderProduct(orderProduct);
            product.addOrderProduct(orderProduct);
            orderProductService.save(orderProduct);
        });

        orderPurchase.setAmount(finalAmount.stream().reduce(Double::sum).orElse(null));
        client.addOrders(orderPurchase);
        System.out.println(orderPurchase.getAmount());
        System.out.println(orderPurchase.getDeliveryAddress());
        orderPurchaseService.save(orderPurchase);
        return new ResponseEntity<>("Purchase order created.", HttpStatus.CREATED);

    }

}
