package pe.com.mallgp.backend.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.mallgp.backend.entities.Admin;
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

    public Offer findById(Long id){
        Offer offer=offerRepository.findById(id).get();
        offer.setStore(null);
        offer.setProduct(null);
        return offer;
    }

    public Offer update(Long id, Offer offer){
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
        return updateOffer;
    }
}
