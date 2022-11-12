package pe.com.mallgp.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pe.com.mallgp.backend.entities.*;
import pe.com.mallgp.backend.repositories.*;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner mappingDemo(
		AdminRepository adminRepository,
		MallRepository mallRepository,
		NewRepository newRepository,
		OfferRepository offerRepository,
		ProductRepository productRepository,
		StoreRepository storeRepository,
		ProductStoreRepository productStoreRepository,
		StoreMallRepository storeMallRepository,
		SuggestionRepository suggestionRepository
		){
		return args->{
			//admins
			Admin admin=new Admin("Luis","123456");
			adminRepository.save(admin);

			Admin admin1=new Admin("Gabriel","123456");
			adminRepository.save(admin1);

			Admin admin2=new Admin("Edwin","123456");
			adminRepository.save(admin2);

			//malls
			Mall mall=new Mall("Larcomar","Miraflores");
			mallRepository.save(mall);
			newRepository.save(new New("Martes cine 50% descuento","19/11/2022" , "19/11/2022", mall));
			newRepository.save(new New("Jueves cine 50% descuento", "19/11/2022","19/11/2022" , mall));


			Mall mall1=new Mall("Megaplaza","Independencia");
			mallRepository.save(mall1);
			newRepository.save(new New("Novedad 2","19-11-2022","19-11-2022", mall1));
			newRepository.save(new New("Novedad 3", "19-11-2022","19-11-2022", mall1));

			Mall mall2=new Mall("Plaza Norte","Independencia");
			mallRepository.save(mall2);
			newRepository.save(new New("Novedad 4", "19-11-2022", "19-11-2022", mall2));
			newRepository.save(new New("Novedad 5", "19-11-2022", "19-11-2022", mall2));

			//products
			Product product=new Product("Zapatilla","Calzado");
			productRepository.save(product);

			Product product1=new Product("Pizza","Alimento");
			productRepository.save(product1);

			Product product2=new Product("Televidor","Tecnologia");
			productRepository.save(product2);

			//stores
			Store store=new Store("addidas","calzado");
			storeRepository.save(store);

			Store store1=new Store("pizza_vegana","alimento");
			storeRepository.save(store1);

			Store store2=new Store("electra","tecnologia");
			storeRepository.save(store2);

			Store store3=new Store("reebok","calzado");
			storeRepository.save(store3);

			//ProductStore
			ProductStore productStore1=new ProductStore(product,store,12.0,"19-11-2022");
			productStoreRepository.save(productStore1);

			//offers
			offerRepository.save(new Offer("Oferta 1","19-11-2022","19-11-2022",store,product));
			offerRepository.save(new Offer("Oferta 2","19-11-2022","19-11-2022",store1,product1));
			offerRepository.save(new Offer("Oferta 3","19-11-2022","19-11-2022",store2,product2));

			//StoreMall
			StoreMall storeMall1=new StoreMall(store,mall,120,admin);
			storeMallRepository.save(storeMall1);

			//Suggestion
			Suggestion suggestion1=new Suggestion("Pablo","Ponganle juegos >:0", "19-11-2022");
			suggestionRepository.save(suggestion1);
		};
	}

}
