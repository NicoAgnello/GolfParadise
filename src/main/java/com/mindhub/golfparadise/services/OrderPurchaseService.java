package com.mindhub.golfparadise.services;

import com.itextpdf.text.DocumentException;
import com.mindhub.golfparadise.dtos.OrderPurchaseDTO;
import com.mindhub.golfparadise.dtos.PurchaseDTO;
import com.mindhub.golfparadise.models.OrderPurchase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface OrderPurchaseService {

    OrderPurchaseDTO getOrderPurchase(Long id);

    List<OrderPurchaseDTO> getOrderPurchases();

    OrderPurchaseDTO findLastOne();

    void save(OrderPurchase orderPurchase);

    public ResponseEntity<Object> generateOrderPurchase(Authentication authentication,
                                                        @RequestBody PurchaseDTO purchaseDTO);

    public void generateOrderPurchasePdf(Authentication authentication,
                                         HttpServletResponse response)
            throws IOException, DocumentException;

}
