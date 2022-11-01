package pe.com.mallgp.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.mallgp.backend.entities.Mall;
import pe.com.mallgp.backend.entities.New;
import pe.com.mallgp.backend.entities.Offer;
import pe.com.mallgp.backend.entities.Product;
import pe.com.mallgp.backend.excepctions.ResourceNotFoundException;
import pe.com.mallgp.backend.repositories.OfferRepository;
import pe.com.mallgp.backend.repositories.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OfferRepository offerRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Product>>getAllProducts(){
        List<Product> products;
        products=productRepository.findAll();
        for(Product p:products){
            p.setOffers(null);
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product newProduct = productRepository.save(new Product(product.getName(),product.getCategory()));
        return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/products/1
    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus>deleteProductById(@PathVariable("id")Long id){
        productRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // http://localhost:8080/api/products/4/forced/1
    @DeleteMapping("/products/{id}/forced/{forced}")
    public ResponseEntity<HttpStatus> deleteProductByIdForced(@PathVariable("id") Long id, @PathVariable("forced") int forced) {

        if (forced==1) {
            Product foundOwner = productRepository.findById(id).
                    orElseThrow(()->new ResourceNotFoundException("Not found Product with id="+id));
            for (Offer offer: foundOwner.getOffers()) {
                offerRepository.deleteById(offer.getId());
            }
        }
        productRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id")Long id, @RequestBody Product product){
        Product foundProduct=productRepository.findById(id).get();
        foundProduct.setName(product.getName());
        foundProduct.setCategory(product.getCategory());
        Product updateProduct=productRepository.save(foundProduct);
        updateProduct.setOffers(null);
        return new ResponseEntity<Product>(updateProduct,HttpStatus.OK);
    }
}
