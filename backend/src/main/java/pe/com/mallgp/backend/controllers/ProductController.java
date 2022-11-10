package pe.com.mallgp.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.mallgp.backend.entities.Mall;
import pe.com.mallgp.backend.entities.Offer;
import pe.com.mallgp.backend.entities.Product;
import pe.com.mallgp.backend.exceptions.ResourceNotFoundException;
import pe.com.mallgp.backend.repositories.OfferRepository;
import pe.com.mallgp.backend.repositories.ProductRepository;
import pe.com.mallgp.backend.services.ProductService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>>getAllProducts(){
        List<Product> products=productService.listAll();
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        //LISTO

    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product newProduct = productService.save(new Product(product.getName(),product.getCategory()));
        return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
        //LISTO
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

        productService.delete(id, forced);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        //LISTO
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){
        Product product=productService.listById(id);
        return new ResponseEntity<Product>(product,HttpStatus.OK);

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
