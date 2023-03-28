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
			Client client3 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("pass1234"));
			Client client4 = new Client("Admin","Admin","admin@admin.com", passwordEncoder.encode("admin"));

			OrderPurchase order1 = new OrderPurchase(1999.98,"Cll Central 234", LocalDateTime.now());
			client1.addOrders(order1);

//			OrderPurchase order2 = new OrderPurchase(78000.00,"cll cruzada 743", LocalDateTime.now());
//			client2.addOrders(order2);

//			Product product1 = new Product("Deep Red Tour Complete Set", "The Callaway XR Packaged Golf Set is packed with ball speed technologies to deliver long distance and complete course coverage through the bag.", "https://www.wilson.com/en-us/media/catalog/product/W/G/WGGC69600__bbbf0d8c83b04373bfe986c054c23576.png?dpr=1&fit=bounds&orient=1&quality=95&optimize=high&format=pjpg&auto=webp&enable=upscale&width=762&height=932&canvas=762%2C932&bg-color=f5f5f5", 1399.99, 5, Category.CLUBS);
			Product product1 = new Product("T//World TW757", "Covering the thin sole slot with carbon strengthens the T// World 757 D Driver’s face, while saving weight and increasing repulsion at impact. This yields the highest initial ball speed in HONMA’s history.", "https://honmagolf.com/storage/product/8934_TW757_1W_D.jpg",599.99, 10, Category.CLUBS);
			Product product2 = new Product("Beres Black Driver", "Dividing the sole slot into three portions helps the clubhead retain its shape instantly at impact -- yielding high repulsion for a much higher initial ball speed.", "https://honmagolf.com/storage/product/8792_B_BK_1W.jpg",549.00,5, Category.CLUBS);
			Product product3 = new Product("Beres Aizu Driver", "The premium BERES brand provides world-class quality and performance, and now collaborates with the traditional Japanese AIZU lacquer, originating in the northern part of Japan close to the Honma Sakata factory", "https://honmagolf.com/storage/product/8784_1_B_AIZU_L1W3S.jpg",449.00,10, Category.CLUBS);
			Product product4 = new Product("Beres Aizu Driver Gold", "The premium BERES brand combines world-class quality and performance with the traditional and colorful Japanese artwork on each clubhead.", "https://honmagolf.com/storage/product/8776_1_B_AIZU_1W3S.jpg",399.00,3, Category.CLUBS);
			Product product5 = new Product("Beres Honma Black Driver", "Larger sweet spot increases ball speed off all parts of the club face.A slot positioned near the leading edge of the sole of the driver flexes at impact to create high initial ball speed and prevent power loss on shots struck away from face center.", "https://honmagolf.com/storage/product/8412_BERES_BK_1W.jpg",349.00,5, Category.CLUBS);
			Product product6 = new Product("Beres Ladies Driver", "Beautifully and elegantly designed for high performance, to inspire a feeling of pride for golfers. Optimized slots create clubface flexion, for more ball speed on mishits. Uneven thickness clubface design.", "https://honmagolf.com/storage/product/5850_HONMA_GOLF_BERES_LADIES_DRIVER_a%CC%81@.jpg",299.00,8, Category.CLUBS);
			Product product7 = new Product("T//World GS Driver", "We enhanced both distance abd forgiveness -- elevating the driver's overall performance.", "https://honmagolf.com/storage/product/8368_1w.jpg",299.00,10, Category.CLUBS);
			Product product8 = new Product("T//World GS Driver Mens", "Pursuing the Best Balance of Speed and Forgiveness sole slots radial face keel design P-SAT Precision Spine Control", "https://honmagolf.com/storage/product/7894_1w.jpg",455.00,7, Category.CLUBS);
			Product product9 = new Product("G430 HL Drivers", "The MAX and SFT models utilize lighter backweights (11g), the Alta Quick shaft and lighter grip to deliver more ball speed with extreme forgiveness. In testing, slower-swing-speed players gained on average nine yards with the HL build.", "https://pingmediastage.azureedge.net/mediastorage/mediastorage/ping-na/medialibrary/clubs/drivers/g430/g430_driver-lst_mouseover_500x500.jpg", 599.00, 10, Category.CLUBS);
			Product product10 = new Product("Women Launcher XL Lite Driver", "A Driver designed to fit your game just makes more sense, doesn’t it? Shorter shafts, retooled shaft flex, and an upgraded grip make the Women’s Launcher XL Lite Driver the perfect fit.", "https://us.dunlopsports.com/on/demandware.static/-/Sites-masterCatalog_DunlopSports/default/dw7bcff770/images/large/CG22-Clubs-Launcher-XL-Driver-Lite-Womens-2.jpeg", 499.99, 12, Category.CLUBS);

			Product product11 = new Product("Honma Caddie Bags CB12213", "Lightweight basic caddie bag with upgraded side pocket functions.", "https://honmagolf.com/storage/product/9090_CB12213_NY_600x600.jpg", 319.99, 10, Category.BAGS);
			Product product12 = new Product("Honma Caddie Bags CB122203", "2022 Pro Tour Replica caddie bag, developed with HONMA Tour Pro golfers.", "https://honmagolf.com/storage/product/9084_CB12203_BK_hood.jpg", 379.99, 5, Category.BAGS);
			Product product13 = new Product("Honma Caddie Bags CB12211", "Lightweight basic caddie bag with upgraded side pocket functions", "https://honmagolf.com/storage/product/9010_CB12211_WH_RED_600x600.jpg", 389.99, 10, Category.BAGS);
			Product product14 = new Product("Honma Caddie Bags CB12209", "Sporty design caddie bag with an accent on the side panels by using the two different glossy materials.", "https://honmagolf.com/storage/product/9008_CB12209_WH_NY%5b1%5d.jpg", 379.99, 12, Category.BAGS);
			Product product15 = new Product("Honma Caddie Bags CB12201", "2022 Pro Tour caddie bag, developed with HONMA Tour Pro golfers. High spec model with excellent functions such as high durability, waterproof fasteners, and cushion pads.", "https://honmagolf.com/storage/product/9004_CB12201_NY.jpg", 369.99, 5, Category.BAGS);
			Product product16 = new Product("Honma Caddie Bags CB12115", "Lightweight caddy bag designed in HONMA D1 ball fluorescent colors with DANCING HONMA logo.", "https://honmagolf.com/storage/product/8120_CB12115_GRAY.jpg", 379.99, 12, Category.BAGS);
			Product product17 = new Product("Honma Caddie Bags CB12114", "POLYESTER | SYNTHETIC LEATHERS (P.V.C) Lightweight caddy bag designed in HONMA D1 ball fluorescent colors with DANCING HONMA logo.", "https://honmagolf.com/storage/product/8116_CB12114_ORANGE.jpg", 359.99, 10, Category.BAGS);
			Product product18 = new Product("Honma Caddie Bags CB12113", "POLYESTER | SYNTHETIC LEATHER (P.V.C）Color BLACK | GRAY | NAVY | WHITE", "https://honmagolf.com/storage/product/8114_CB12113_BLACK.jpg", 349.99, 10, Category.BAGS);
			Product product19 = new Product("Honma Caddie Bags CB12102", "This stylish, high-strength caddy bag features waterproof zippers and high strength core material.", "https://honmagolf.com/storage/product/8084_CB12102_RED.jpg", 399.99, 5, Category.BAGS);
			Product product20 = new Product("Honma Pro Stand Bag CB12002", "This 2020 Tournament Spec professional stand bag is fully equipped water repellent zipper and all the functionailty of a staff bag in stand bag frame", "https://honmagolf.com/storage/product/5990_CB12002_SAX_NY.jpg", 339.99,10, Category.BAGS);

			Product product21 = new Product("Honma Golf Balls 2020 model", "The No.1 best-selling ball* has evolved under the concept of “More Distance”. The newly compounded soft rubber core boosts the distance performance while maintaining beautiful feeling.", "https://honmagolf.com/storage/product/8624_7816_1_white.jpg", 89.99, 5, Category.BALLS);
			Product product22 = new Product("Honma Golf Balls X4", "Structure: 4-piece Color: White/Yellow Cover: Urethane Cover Core: Ultra Soft Core Dimple: 326 Dimples", "https://honmagolf.com/storage/product/5842_X4_1.jpg", 79.99, 10, Category.BALLS);
			Product product23 = new Product("Honma Golf Balls TW-S", "Structure: 3-piece Color: White/Yellow Cover: Urethane Cover Core: S-Fast Core Dimple: 326 Dimples", "https://honmagolf.com/storage/product/5840_1106131452_5dc248bcac482.jpg", 84.99, 5, Category.BALLS);
			Product product24 = new Product("Honma Golf Balls TW-X", "Structure: 3-piece Color: White/Yellow Cover: Urethane Cover Core: Super Fast NC Core Dimple: 326 Dimples", "https://honmagolf.com/storage/product/5838_1106130114_5dc2458abc01e.jpg", 79.99, 8, Category.BALLS);
			Product product25 = new Product("Honma Golf Balls Honma A1", "Structure: 2-piece  Color: White/Yellow/Orange/Muti-color (White, Yellow, Orange, Pink)  Cover: Ionomer Cover Core: Newly Developed Super Soft Core Dimple: 368 Dimples", "https://honmagolf.com/storage/product/5836_A1.jpg", 79.99, 10, Category.BALLS);
			Product product26 = new Product("Honma Golf Balls D1 Plus", "Structure: 3-piece ball Color: White/Multi-color (White/Yellow/Orange/Pink) Cover: Ionomer cover Core: High-resilience spring rubber core Dimple: 368 dimples", "https://honmagolf.com/storage/product/5192_Honmagolf_ball_D1plus.jpg", 81.99, 10, Category.BALLS);
			Product product27 = new Product("Honma Golf Balls Future XX", "Structure: 6-piece  Cover :  Soft polyurethane Layer 2: Highly resilient ionic polymer layer Layer 3: High-rebound layer Layer 4 : Intermediate soft layer Layer 5 : Soft layer Core: Soft rubber core Dimple : 326", "https://honmagolf.com/storage/product/5186_honmagolf_ball_futurexx1.jpg",69.99, 8, Category.BALLS);
			Product product28 = new Product("Pro V1X","Loyalty Rewarded: Buy 3 Dozen Pro V1, Pro V1x, Pro V1x Left Dash, or AVX and get the 4th dozen free. All 4 dozen must be identical. No logos or double personalization. Limit of 1 free dozen per customer. 4th dozen must be added to your Cart to qualify.", "https://www.titleist.com/dw/image/v2/AAZW_PRD/on/demandware.static/-/Sites-titleist-master/default/dwe5458002/T2048S_01.png?sw=650&sh=650&sm=fit&sfrm=png", 55.00, 12, Category.BALLS);
			Product product29 = new Product("RB 566 Golf Ball", "Mizuno’s softest compression ball matched with 566 micro-dimple design – engineered to delay the rate of descent past the apex of flight. Stable, mid trajectory with the driver and nimble around the greens.", "https://mizunogolf.com/usa/wp-content/uploads/sites/5/2020/01/RB566_12Pack_USA.jpg", 22.00, 10, Category.BALLS);
			Product product30 = new Product("TP5 Pix Collegiate Golf Balls", "Whether you’re an alum or a fan, the TP5 pix Alabama Crimson Tide lets you put your team spirit on full display. With 12 officially licensed logos, you can rep your team while getting improved alignment and instant visual feedback.", "https://www.taylormadegolf.com/on/demandware.static/-/Sites-TMaG-Library/en_US/v1679803764495/TaylorMade/plp/2021/ncaa/images/NCAA-Golf-Balls.jpg", 54.99, 10, Category.BALLS);

			Product product31 = new Product("Tour Elite", "Preferred by worldwide Tour Professionals, the new Tour Elite Hat is a fitted design with a standard curve bill that features a 3D embroidered Titleist script and lightweight honeycomb mesh material for a comfortable fit and distinctive look.", "https://www.titleist.com/dw/image/v2/AAZW_PRD/on/demandware.static/-/Sites-titleist-master/default/dweff9de22/TH23FTEL-10_01.png?sw=650&sh=650&sm=fit&sfrm=png", 37.00, 8,Category.CLOTHES);
			Product product32 = new Product("Montauk Visor", "The Montauk Visor features a classic low-profile design and lightweight cotton materials, making it the perfect visor for golfers seeking a stylish, relaxed fit.", "https://www.titleist.com/dw/image/v2/AAZW_PRD/on/demandware.static/-/Sites-titleist-master/default/dw3d52f58d/TH22VMT-10_01.png?sw=650&sh=650&sm=fit&sfrm=png", 30.00, 15, Category.CLOTHES);
			Product product33 = new Product("Honma F1 Gloves GV12004", "This glove features a universal fit design for sizes 22 cm to 25 cm. Made of stretch material for a perfect fit for all, the glove is designed with embossed synthetic leather for an improved grip.", "https://honmagolf.com/storage/product/6026_GV12004_NY_BK_img.jpg", 32.00, 8, Category.CLOTHES);
			Product product34 = new Product("A1 NANO Gloves GV12003", "This all-weather glove, perfect in rain or sunny conditions, is constructed of unique Nanofront® material for increased action in wet conditions.", "https://honmagolf.com/storage/product/6024_GV12003_img.jpg",19.99,6,Category.CLOTHES);
			Product product35 = new Product("P1 Gloves GV12002", "The P1 glove is the choice of touring professionals. Constructed from Japanese man-made leather (Clarino),  features two different materials on the palm and back of the hand.", "https://honmagolf.com/storage/product/6022_GV12002_img.jpg",21.99, 12, Category.CLOTHES);
			Product product36 = new Product("Honma Gloves GA-1902", "Material Artificial Leather Color White Weight\t15g Size 21~26cm Origin Indonesia", "https://honmagolf.com/storage/product/5416_HONMA%20GLOVES%20GA1902_WH_2.jpg", 22.99, 8,Category.CLOTHES);
			Product product37 = new Product("Players Men's", "The choice of leading professionals, the Players™ Men's Golf Glove features a strong base of tanned cabretta leather complemented with well-thought-out and precisely placed seams.", "https://www.titleist.com/dw/image/v2/AAZW_PRD/on/demandware.static/-/Sites-titleist-master/default/dwafee3bbb/6629E_02.png?sw=650&sh=650&sm=fit&sfrm=png", 26.00, 13, Category.CLOTHES);
			Product product38 = new Product("PING Tour Glove", "Solite® Premium Cabretta leather with Micro-Pur™ technology is engineered for the ultimate in feel, softness and performance. Finger vents and perforations optimize breathability, and the wristband features moisture-wicking SensorCool Technology.", "https://pingmediastage.azureedge.net/mediastorage/mediastorage/ping-na/medialibrary/ecommerce/2020/gloves/ping_tour-back_h__0673_708x708.jpg?sku=34723-16", 30.00, 9,Category.CLOTHES);
			Product product39 = new Product("Tour Visor", "Designed with 100% organic cotton material, the Tour Visor features a classic, high crown style and easy-adjust velcro closure.", "https://www.titleist.com/dw/image/v2/AAZW_PRD/on/demandware.static/-/Sites-titleist-master/default/dw74ae5fe4/TH9VHPTS-1_01.png?sw=650&sh=650&sm=fit&sfrm=png", 35.00, 13, Category.CLOTHES);
			Product product40 = new Product("Tour Preferred Glove", "The glove of choice for Team TaylorMade Tour athletes, the Tour Preferred Glove is constructed from premium leather to provide optimal comfort, fit and feel.", "https://www.taylormadegolf.com/dw/image/v2/AAIS_PRD/on/demandware.static/-/Sites-tmag-master-catalog/en_US/v1679890037971/zoom/TA858_zoom_D.jpg?sw=900&sh=900&sm=fit", 24.99, 14, Category.CLOTHES);

			OrderProduct orderProduct1 = new OrderProduct(1, 1399.99);
			order1.addOrderProduct(orderProduct1);
			product1.addOrderProduct(orderProduct1);

			OrderProduct orderProduct2 = new OrderProduct(1, 599.99);
			order1.addOrderProduct(orderProduct2);
			product2.addOrderProduct(orderProduct2);

			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);
			clientRepository.save(client4);

			productRepository.save(product1);
			productRepository.save(product2);
			productRepository.save(product3);
			productRepository.save(product4);
			productRepository.save(product5);
			productRepository.save(product6);
			productRepository.save(product7);
			productRepository.save(product8);
			productRepository.save(product9);
			productRepository.save(product10);
			productRepository.save(product11);
			productRepository.save(product12);
			productRepository.save(product13);
			productRepository.save(product14);
			productRepository.save(product15);
			productRepository.save(product16);
			productRepository.save(product17);
			productRepository.save(product18);
			productRepository.save(product19);
			productRepository.save(product20);
			productRepository.save(product21);
			productRepository.save(product22);
			productRepository.save(product23);
			productRepository.save(product24);
			productRepository.save(product25);
			productRepository.save(product26);
			productRepository.save(product27);
			productRepository.save(product28);
			productRepository.save(product29);
			productRepository.save(product30);
			productRepository.save(product31);
			productRepository.save(product32);
			productRepository.save(product33);
			productRepository.save(product34);
			productRepository.save(product35);
			productRepository.save(product36);
			productRepository.save(product37);
			productRepository.save(product38);
			productRepository.save(product39);
			productRepository.save(product40);

			orderRepository.save(order1);
//			orderRepository.save(order2);

			orderProductRepository.saveAll(List.of(orderProduct1,orderProduct2));
		};
	}

}
;