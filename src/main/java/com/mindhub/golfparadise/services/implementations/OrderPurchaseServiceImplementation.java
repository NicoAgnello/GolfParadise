package com.mindhub.golfparadise.services.implementations;

import com.itextpdf.text.DocumentException;
import com.mindhub.golfparadise.dtos.OrderProductDTO;
import com.mindhub.golfparadise.dtos.OrderPurchaseDTO;
import com.mindhub.golfparadise.dtos.ProductDTO;
import com.mindhub.golfparadise.dtos.PurchaseDTO;
import com.mindhub.golfparadise.models.*;
import com.mindhub.golfparadise.repositories.ClientRepository;
import com.mindhub.golfparadise.repositories.OrderProductRepository;
import com.mindhub.golfparadise.repositories.OrderRepository;
import com.mindhub.golfparadise.repositories.ProductRepository;
import com.mindhub.golfparadise.services.OrderPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderPurchaseServiceImplementation implements OrderPurchaseService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;

    @Override
    public OrderPurchaseDTO getOrderPurchase(Long id) {
        return orderRepository.findById(id).map(OrderPurchaseDTO::new).orElse(null);
    }

    @Override
    public List<OrderPurchaseDTO> getOrderPurchases() {
        return orderRepository.findAll().stream().map(OrderPurchaseDTO::new).collect(Collectors.toList());
    }

    @Override
    public OrderPurchaseDTO findLastOne() {
        return new OrderPurchaseDTO(orderRepository.findFirstByOrderByIdDesc());
    }

    @Override
    public void save(OrderPurchase orderPurchase) {
        orderRepository.save(orderPurchase);
    }

    @Override
    public ResponseEntity<Object> generateOrderPurchase(Authentication authentication,
                                                        @RequestBody PurchaseDTO purchaseDTO) {
        Client client = clientRepository.findByEmail(authentication.getName());
        ArrayList<OrderProductDTO> orderProducts = purchaseDTO.getOrderProducts();
        ArrayList<Double> finalAmount = new ArrayList<>();
        boolean validOrderProduct = true;
        boolean enoughStock = true;
        for (OrderProductDTO orderProductDTO : orderProducts) {
            Product product = productRepository.findById(orderProductDTO.getProductId()).orElse(null);
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

        OrderPurchase orderPurchase = new OrderPurchase(0.0, purchaseDTO.getDeliveryAddress(), purchaseDTO.getDeliveryCost(), LocalDateTime.now());
        orderRepository.save(orderPurchase);

        orderProducts.forEach(orderProductDTO -> {
            Product product = productRepository.findById(orderProductDTO.getProductId()).orElse(null);
            product.setStock(product.getStock() - orderProductDTO.getQuantity());
            productRepository.save(product);
            double totalAmount = orderProductDTO.getQuantity() * product.getPrice();
            finalAmount.add(totalAmount);
            OrderProduct orderProduct = new OrderProduct(orderProductDTO.getQuantity(), totalAmount);
            orderPurchase.addOrderProduct(orderProduct);
            product.addOrderProduct(orderProduct);
            orderProductRepository.save(orderProduct);
        });

        orderPurchase.setAmount(finalAmount.stream().reduce(Double::sum).orElse(null));
        client.addOrders(orderPurchase);
        orderRepository.save(orderPurchase);
        return new ResponseEntity<>("Purchase order created.", HttpStatus.CREATED);
    }

    @Override
    public void generateOrderPurchasePdf(Authentication authentication, HttpServletResponse response) throws IOException, DocumentException {
        Client client = clientRepository.findByEmail(authentication.getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        response.setContentType("application/pdf");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=order_details_" + currentDateTime.format(formatter) + ".pdf";
        response.setHeader(headerKey, headerValue);

        Set<OrderPurchaseDTO> clientOrders = client.getOrders().stream().map(OrderPurchaseDTO::new).collect(Collectors.toSet());
        List<OrderPurchaseDTO> sortedOrders = clientOrders.stream().sorted(Comparator.comparing(OrderPurchaseDTO::getDate)).collect(Collectors.toList());
        OrderPurchaseDTO lastOrder = sortedOrders.get(sortedOrders.size() - 1);
        Set<OrderProductDTO> orderProducts = lastOrder.getOrderProducts();

        Pdf pdf = new Pdf();
        pdf.createDocument(response);
        pdf.addTitle("GOLF PARADISE");
        pdf.addLineJumps();
        pdf.addLineJumps();
        pdf.addSubTitle("Order #GP0000" + lastOrder.getId());
        pdf.addLineJumps();
        pdf.addParagraph("Date: " + currentDateTime.format(formatter));
        pdf.addParagraph("Client: " + client.getFirstName() + " " + client.getLastName());
        pdf.addParagraph("Delivery address: " + lastOrder.getDeliveryAddress());
        pdf.addParagraph("Delivery cost: $" + lastOrder.getDeliveryCost());
        pdf.addLineJumps();
        pdf.addLineJumps();
        pdf.addOrderProductsTable(orderProducts);
        pdf.addLineJumps();
        pdf.addTotalAmountTable(lastOrder);
        pdf.closeDocument();
    }
}
