package pe.com.mallgp.backend.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.com.mallgp.backend.entities.Offer;
import pe.com.mallgp.backend.exceptions.ResourceNotFoundException;
import pe.com.mallgp.backend.repositories.OfferRepository;
import pe.com.mallgp.backend.services.OfferService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    OfferRepository offerRepository;

    public List<Offer> listAll() {
        List<Offer> offers;
        offers=offerRepository.findAll();

        for(Offer o:offers){
            o.setStore(null);
            o.setProduct(null);
        }
        return offers;
    }

    @Transactional
    public Offer save(Offer offer){
        Offer newOffer = offerRepository.save(new Offer(offer.getName(),offer.getDate_on(),offer.getDate_of(),offer.getStore(),offer.getProduct()));
        return newOffer;
    }

    @Transactional
    public void delete(Long id, int forced){
        if (forced==1) {
            Offer foundOffer = offerRepository.findById(id).
                    orElseThrow(()->new ResourceNotFoundException("Not found Offer with id="+id));

        }
        offerRepository.deleteById(id);
    }

    public Offer listById(Long id){
        Offer offer=offerRepository.findById(id).get();
        offer.setStore(null);
        offer.setProduct(null);
        return offer;
    }

}
