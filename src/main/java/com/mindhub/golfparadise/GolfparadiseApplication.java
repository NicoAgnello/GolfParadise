package com.mindhub.golfparadise;

import com.mindhub.golfparadise.models.*;


import com.mindhub.golfparadise.repositories.ClientRepository;
import com.mindhub.golfparadise.repositories.OrderProductRepository;
import com.mindhub.golfparadise.repositories.OrderRepository;
import com.mindhub.golfparadise.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SpringBootApplication
public class GolfparadiseApplication {

	public static void main(String[] args) {

		SpringApplication.run(GolfparadiseApplication.class, args);

	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, OrderRepository orderRepository, ProductRepository productRepository , OrderProductRepository orderProductRepository) {
		return (args) -> {
			Client client1 = new Client (1L,"Rory","Mcylroy","rm@gmail.com","*******");
			Client client2 = new Client(2L,"Tiger","Woods","tw@gmail.com","*******");
			clientRepository.save(client1);
			clientRepository.save(client2);
			OrderPurchase order1 = new OrderPurchase(1L,5000.00,"cll central 234", LocalDateTime.now());
			client1.addProducts(order1);
			orderRepository.save(order1);
			OrderPurchase order2 = new OrderPurchase(2L,78000.00,"cll cruzada 743", LocalDateTime.now());
			client2.addProducts(order2);
			orderRepository.save(order2);
			Product ProV1 = new Product(1L,"ball", "ball pro V1", "https/img.com",30.00,5, Categories.BALLS);
			productRepository.save(ProV1);
			Product TSR2  = new Product(1L,"driver", "TSR2 Driver", "https/img.com",599.00,60, Categories.CLUBS);
			productRepository.save(TSR2);
			Product MidSizeBag = new Product(1L,"bag", "Mid Size Bag", "https/img.com",380.00,3, Categories.BAGS);
			productRepository.save(MidSizeBag);








		};
	}

}
;