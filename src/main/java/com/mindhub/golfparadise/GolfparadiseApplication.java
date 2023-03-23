package com.mindhub.golfparadise;

import com.mindhub.golfparadise.models.*;


import com.mindhub.golfparadise.repositories.ClientRepository;
import com.mindhub.golfparadise.repositories.OrderProductRepository;
import com.mindhub.golfparadise.repositories.OrderRepository;
import com.mindhub.golfparadise.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SpringBootApplication
public class GolfparadiseApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(GolfparadiseApplication.class, args);

	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  OrderRepository orderRepository,
									  ProductRepository productRepository,
									 OrderProductRepository orderProductRepository) {
		return (args) -> {

			Client client1 = new Client ("Rory","Mcylroy","rm@gmail.com", passwordEncoder.encode("password1"));
			Client client2 = new Client("Tiger","Woods","tw@gmail.com", passwordEncoder.encode("password2"));
			Client client3 = new Client("Admin","Admin","admin@admin.com", passwordEncoder.encode("admin"));

			OrderPurchase order1 = new OrderPurchase(659.00,"cll central 234", LocalDateTime.now());
			client1.addOrders(order1);

//			OrderPurchase order2 = new OrderPurchase(78000.00,"cll cruzada 743", LocalDateTime.now());
//			client2.addOrders(order2);

			Product proV1 = new Product("ball", "ball pro V1", "https/img.com",30.00,5, Category.BALLS);

			Product tsr2  = new Product("driver", "TSR2 Driver", "https/img.com",599.00,60, Category.CLUBS);

			Product midSizeBag = new Product("bag", "Mid Size Bag", "https/img.com",380.00,3, Category.BAGS);

			OrderProduct orderProduct1 = new OrderProduct(2, 60.0);
			order1.addOrderProduct(orderProduct1);
			proV1.addOrderProduct(orderProduct1);


			OrderProduct orderProduct2 = new OrderProduct(1, 599.0);
			order1.addOrderProduct(orderProduct2);
			tsr2.addOrderProduct(orderProduct2);


			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);

			productRepository.save(proV1);
			productRepository.save(tsr2);
			productRepository.save(midSizeBag);

			orderRepository.save(order1);
//			orderRepository.save(order2);

			orderProductRepository.saveAll(List.of(orderProduct1,orderProduct2));
		};
	}

}
;