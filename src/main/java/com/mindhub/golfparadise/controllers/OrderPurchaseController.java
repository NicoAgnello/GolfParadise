package com.mindhub.golfparadise.controllers;

import com.itextpdf.text.DocumentException;
import com.mindhub.golfparadise.dtos.DeliveryCostDTO;
import com.mindhub.golfparadise.dtos.OrderPurchaseDTO;
import com.mindhub.golfparadise.dtos.PurchaseDTO;
import com.mindhub.golfparadise.services.ClientService;
import com.mindhub.golfparadise.services.OrderProductService;
import com.mindhub.golfparadise.services.OrderPurchaseService;
import com.mindhub.golfparadise.services.ProductService;
import com.mindhub.golfparadise.utils.OrderPurchaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

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

    @GetMapping("/order-purchases")
    public List<OrderPurchaseDTO> getOrderPurchases() {
        return orderPurchaseService.getOrderPurchases();
    }

    @Transactional
    @PostMapping("/clients/current/order-purchases/generate")
    public ResponseEntity<Object> generateOrderPurchase(Authentication authentication,
                                                        @RequestBody PurchaseDTO purchaseDTO) {
        return orderPurchaseService.generateOrderPurchase(authentication, purchaseDTO);
    }

    @GetMapping("/clients/current/pdf/generate")
    public void generateOrderPurchasePdf(Authentication authentication,
                                         HttpServletResponse response)
            throws IOException, DocumentException {
        orderPurchaseService.generateOrderPurchasePdf(authentication, response);
    }

    @PostMapping("/clients/current/delivery-cost")
    public DeliveryCostDTO getDeliveryCost(Authentication authentication, @RequestParam String zipCode) {
        return new DeliveryCostDTO(OrderPurchaseUtil.getDeliveryCost(Short.parseShort(zipCode)));
    }

}