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

			Client client1 = new Client ("Rory","McIlroy","rm@gmail.com", passwordEncoder.encode("password1"));
			Client client2 = new Client("Tiger","Woods","tw@gmail.com", passwordEncoder.encode("password2"));
			Client client3 = new Client("Admin","Admin","admin@admin.com", passwordEncoder.encode("admin"));

			OrderPurchase order1 = new OrderPurchase(659.00,"Cll Central 234", LocalDateTime.now());
			client1.addOrders(order1);

//			OrderPurchase order2 = new OrderPurchase(78000.00,"cll cruzada 743", LocalDateTime.now());
//			client2.addOrders(order2);

			Product product1 = new Product("Beres Black Driver", "Dividing the sole slot into three portions helps the clubhead retain its shape instantly at impact -- yielding high repulsion for a much higher initial ball speed.", "https://honmagolf.com/storage/product/8792_B_BK_1W.jpg",549.00,5, Category.CLUBS);
			Product product2 = new Product("Beres Aizu Driver", "The premium BERES brand provides world-class quality and performance, and now collaborates with the traditional Japanese AIZU lacquer, originating in the northern part of Japan close to the Honma Sakata factory", "https://honmagolf.com/storage/product/8784_1_B_AIZU_L1W3S.jpg",449.00,10, Category.CLUBS);
			Product product3 = new Product("Beres Aizu Driver Gold", "The premium BERES brand combines world-class quality and performance with the traditional and colorful Japanese artwork on each clubhead.", "https://honmagolf.com/storage/product/8776_1_B_AIZU_1W3S.jpg",399.00,3, Category.CLUBS);
			Product product4 = new Product("Beres Honma Black Driver", "Larger sweet spot increases ball speed off all parts of the club face.A slot positioned near the leading edge of the sole of the driver flexes at impact to create high initial ball speed and prevent power loss on shots struck away from face center.", "https://honmagolf.com/storage/product/8412_BERES_BK_1W.jpg",349.00,5, Category.CLUBS);
			Product product5 = new Product("Beres Ladies Driver", "Beautifully and elegantly designed for high performance, to inspire a feeling of pride for golfers. Optimized slots create clubface flexion, for more ball speed on mishits. Uneven thickness clubface design.", "https://honmagolf.com/storage/product/5850_HONMA_GOLF_BERES_LADIES_DRIVER_a%CC%81@.jpg",299.00,8, Category.CLUBS);
			Product product6 = new Product("T//World GS Driver", "We enhanced both distance abd forgiveness -- elevating the driver's overall performance.", "https://honmagolf.com/storage/product/8368_1w.jpg",299.00,10, Category.CLUBS);
			Product product7 = new Product("T//World GS Driver Mens", "Pursuing the Best Balance of Speed and Forgiveness sole slots radial face keel design P-SAT Precision Spine Control", "https://honmagolf.com/storage/product/7894_1w.jpg",455.00,7, Category.CLUBS);


			OrderProduct orderProduct1 = new OrderProduct(2, 60.0);
			order1.addOrderProduct(orderProduct1);
			product1.addOrderProduct(orderProduct1);


			OrderProduct orderProduct2 = new OrderProduct(1, 599.0);
			order1.addOrderProduct(orderProduct2);
			product2.addOrderProduct(orderProduct2);


			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);

			productRepository.save(product1);
			productRepository.save(product2);
			productRepository.save(product3);
			productRepository.save(product4);
			productRepository.save(product5);
			productRepository.save(product6);
			productRepository.save(product7);

			orderRepository.save(order1);
//			orderRepository.save(order2);

			orderProductRepository.saveAll(List.of(orderProduct1,orderProduct2));
		};
	}

}
;