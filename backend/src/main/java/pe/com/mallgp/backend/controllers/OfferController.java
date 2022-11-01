package pe.com.mallgp.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.mallgp.backend.entities.Offer;
import pe.com.mallgp.backend.entities.Product;
import pe.com.mallgp.backend.repositories.OfferRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OfferController {
    @Autowired
    private OfferRepository offerRepository;

    @GetMapping("/offers")
    public ResponseEntity<List<Offer>> getAllOffers(){
        List<Offer> offers;
        offers=offerRepository.findAll();
        for(Offer o:offers){
            o.setStore(null);
            o.setProduct(null);
        }
        return new ResponseEntity<List<Offer>>(offers, HttpStatus.OK);
    }

    @PostMapping("/offers")
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer){
        Offer newOffer = offerRepository.save(new Offer(offer.getName(),offer.getDate_on(),offer.getDate_of(),offer.getStore(),offer.getProduct()));
        return new ResponseEntity<Offer>(newOffer, HttpStatus.CREATED);
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable("id") Long id){
        Offer offer=offerRepository.findById(id).get();
        offer.setStore(null);
        offer.setProduct(null);
        return new ResponseEntity<Offer>(offer, HttpStatus.OK);
    }

    @PutMapping("/offers/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable("id")Long id, @RequestBody Offer offer){
        Offer foundOffer=offerRepository.findById(id).get();
        if(offer.getName()!=null)
        foundOffer.setName(offer.getName());
        if(offer.getDate_on()!=null)
        foundOffer.setDate_on(offer.getDate_on());
        if(offer.getDate_of()!=null)
        foundOffer.setDate_of(offer.getDate_of());
        if(offer.getStore()!=null)
        foundOffer.setStore(offer.getStore());
        if(offer.getProduct()!=null)
        foundOffer.setProduct(offer.getProduct());
        Offer updateOffer=offerRepository.save(foundOffer);
        updateOffer.setStore(null);
        updateOffer.setProduct(null);
        return new ResponseEntity<Offer>(updateOffer,HttpStatus.OK);
    }
}
