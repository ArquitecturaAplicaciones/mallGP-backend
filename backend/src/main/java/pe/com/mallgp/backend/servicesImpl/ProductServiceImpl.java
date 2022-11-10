package pe.com.mallgp.backend.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.com.mallgp.backend.entities.Offer;
import pe.com.mallgp.backend.entities.Product;
import pe.com.mallgp.backend.exceptions.ResourceNotFoundException;
import pe.com.mallgp.backend.repositories.OfferRepository;
import pe.com.mallgp.backend.repositories.ProductRepository;
import pe.com.mallgp.backend.services.ProductService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OfferRepository offerRepository;
    public List<Product> listAll(){
        List<Product> products;
        products=productRepository.findAll();
        for(Product p:products){
            p.setOffers(null);
        }
        return products;
    }

    @Transactional
    public Product save(Product product){
        Product newProduct = productRepository.save(new Product(product.getName(),product.getCategory()));
        return newProduct;
    }

    @Transactional
    public void delete(Long id, int forced){
        if (forced==1) {
            Product foundOffer = productRepository.findById(id).
                    orElseThrow(()->new ResourceNotFoundException("Not found Product with id="+id));
            for (Offer offer: foundOffer.getOffers()) {
                offerRepository.deleteById(offer.getId());
            }
        }
        productRepository.deleteById(id);
    }

    public Product listById(Long id){
        Product product=productRepository.findById(id).get();
        product.setOffers(null);
        return product;
    }
}
