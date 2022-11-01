package pe.com.mallgp.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.mallgp.backend.entities.*;
import pe.com.mallgp.backend.repositories.StoreRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @GetMapping("/stores")
    public ResponseEntity<List<Store>> getAllStore(){
        List<Store>stores;
        stores=storeRepository.findAll();
        for (Store s:stores){
            s.setOffers(null);
        }
        return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
    }

    @GetMapping("/stores_offers")
    public ResponseEntity<List<Store>> getAllStoreAndOffers(){
        List<Store>stores;
        stores=storeRepository.findAll();
        for (Store s:stores){
            for (Offer o:s.getOffers()){
                o.setStore(null);
                o.setProduct(null);
            }
        }
        return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
    }

    @PostMapping("/stores")
    public ResponseEntity<Store> createStore(@RequestBody Store store){
        Store newStore = storeRepository.save(new Store(store.getName(),store.getCategory()));
        return new ResponseEntity<Store>(newStore, HttpStatus.CREATED);
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
