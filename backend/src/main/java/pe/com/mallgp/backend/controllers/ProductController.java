package pe.com.mallgp.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.mallgp.backend.entities.Mall;
import pe.com.mallgp.backend.entities.Product;
import pe.com.mallgp.backend.repositories.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

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

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){
        Product product=productRepository.findById(id).get();
        product.setOffers(null);
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