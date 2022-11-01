package pe.com.mallgp.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.mallgp.backend.entities.*;
import pe.com.mallgp.backend.exceptions.ResourceNotFoundException;
import pe.com.mallgp.backend.repositories.OfferRepository;
import pe.com.mallgp.backend.repositories.StoreRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OfferRepository offerRepository;

    @GetMapping("/stores")
    public ResponseEntity<List<Store>> getAllStore(){
        List<Store>stores;
        stores=storeRepository.findAll();
        for (Store s:stores){
            s.setOffers(null);
        }
        return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
    }

    @GetMapping("/stores_products")
    public ResponseEntity<List<Store>> getAllStoreAndProducts(){
        List<Store>stores;
        stores=storeRepository.findAll();
        for (Store s:stores){
            for (ProductStore p:s.getProductStores()){
                p.getProduct().setName(null);
            }
        }
        return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
    }

    @PostMapping("/stores")
    public ResponseEntity<Store> createStore(@RequestBody Store store){
        Store newStore = storeRepository.save(new Store(store.getName(),store.getCategory()));
        return new ResponseEntity<Store>(newStore, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/products/1
    @DeleteMapping("/stores/{id}")
    public ResponseEntity<HttpStatus>deleteStoreById(@PathVariable("id")Long id){
        storeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // http://localhost:8080/api/products/4/forced/1
    @DeleteMapping("/stores/{id}/forced/{forced}")
    public ResponseEntity<HttpStatus> deleteProductByIdForced(@PathVariable("id") Long id, @PathVariable("forced") int forced) {

        if (forced==1) {
            Store foundOwner = storeRepository.findById(id).
                    orElseThrow(()->new ResourceNotFoundException("Not found Store with id="+id));
            for (Offer offer: foundOwner.getOffers()) {
                offerRepository.deleteById(offer.getId());
            }
        }
        storeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/stores/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable("id")Long id){
        Store store=storeRepository.findById(id).get();
        store.setOffers(null);
        return new ResponseEntity<Store>(store,HttpStatus.OK);
    }

    @PutMapping("/stores/{id}")
    public ResponseEntity<Store>updateStore(@PathVariable("id") Long id, @RequestBody Store store){
        Store foundStore=storeRepository.findById(id).get();
        if(store.getName()!=null)
        foundStore.setName(store.getName());
        if(store.getCategory()!=null)
        foundStore.setCategory(store.getCategory());
        Store updateStore=storeRepository.save(foundStore);
        updateStore.setOffers(null);
        return new ResponseEntity<Store>(updateStore, HttpStatus.OK);
    }
}
