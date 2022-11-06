package pe.com.mallgp.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.com.mallgp.backend.entities.Offer;
import pe.com.mallgp.backend.entities.ProductStore;
import pe.com.mallgp.backend.entities.Store;
import pe.com.mallgp.backend.exceptions.ResourceNotFoundException;
import pe.com.mallgp.backend.repositories.ProductRepository;
import pe.com.mallgp.backend.repositories.ProductStoreRepository;
import pe.com.mallgp.backend.repositories.StoreRepository;

@RestController
@RequestMapping("/api")
public class ProductStoreController {
    @Autowired
    private ProductStoreRepository productStoreRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;


    @GetMapping("/productstores/{id}")
    public ResponseEntity<ProductStore> getProductStoreById(@PathVariable("id") Long id){
        ProductStore productStore=productStoreRepository.findById(id).get();
        productStore.setStore(null);
        productStore.setProduct(null);
        return new ResponseEntity<ProductStore>(productStore, HttpStatus.OK);
    }


   /* @GetMapping("/products/{id}/stores/{id2}")
    public ResponseEntity<HttpStatus> getProductByIdStores(@PathVariable("id") Long id, @PathVariable("id2") int forced) {



    } */

}
