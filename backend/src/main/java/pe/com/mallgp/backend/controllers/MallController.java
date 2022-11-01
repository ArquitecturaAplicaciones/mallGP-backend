package pe.com.mallgp.backend.controllers;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.mallgp.backend.entities.*;
import pe.com.mallgp.backend.excepctions.ResourceNotFoundException;
import pe.com.mallgp.backend.repositories.AdminRepository;
import pe.com.mallgp.backend.repositories.MallRepository;
import pe.com.mallgp.backend.repositories.NewRepository;
import pe.com.mallgp.backend.repositories.StoreRepository;

import javax.validation.constraints.Null;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MallController {
    @Autowired
    private MallRepository mallRepository;

    @Autowired
    private NewRepository newRepository;

    @Autowired
    private StoreRepository storeRepository;

    @GetMapping("/malls")
    public ResponseEntity<List<Mall>>getAllMall(){
        List<Mall>malls;
        malls=mallRepository.findAll();
        for (Mall m:malls){
            m.setNews(null);
        }
        return new ResponseEntity<List<Mall>>(malls, HttpStatus.OK);
    }

    @GetMapping("/mall_location")
    public ResponseEntity<List<Mall>>getAllMallAndLocation(){
        List<Mall>malls;
        malls=mallRepository.findAll();
        for (Mall m:malls){
            m.setLocation(null);
        }
        return new ResponseEntity<List<Mall>>(malls, HttpStatus.OK);
    }

    @GetMapping("/mall_stores")
    public ResponseEntity<List<Mall>>getAllMallAndStores(){
        List<Mall>malls;
        malls=mallRepository.findAll();
        for (Mall m: malls) {
            for (StoreMall s: m.getStoreMalls()) {
                s.getStore().setName(null);

            }
        }
        return new ResponseEntity<List<Mall>>(malls, HttpStatus.OK);
    }
    //http://localhost:8080/api/malls
    @PostMapping("/malls")
    public ResponseEntity<Mall> createMall(@RequestBody Mall mall){
        Mall newMall = mallRepository.save(new Mall(mall.getName(),mall.getLocation()));
        return new ResponseEntity<Mall>(newMall, HttpStatus.CREATED);
    }


    // http://localhost:8080/api/malls/1
    @GetMapping("/malls/{id}")
    public ResponseEntity<Mall> getMallById(@PathVariable("id") Long id){
        Mall mall = mallRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Not found Mall with id="+id));

        mall.setNews(null);
        return new ResponseEntity<Mall>(mall,HttpStatus.OK);
    }

    /* @PutMapping("/malls/{id}")
    public ResponseEntity<Mall> updateMallById(@PathVariable("id") Long id,@RequestBody Mall mall){
        Mall foundMall = mallRepository.findById(id).get();
        if(mall.getName()!= null)
            foundMall.setName(mall.getName());
        if(mall.getLocation()!= null)
            foundMall.setLocation(mall.getLocation());
        Mall updateMall=mallRepository.save(foundMall);
        updateMall.setNews(null);
        updateMall.setStoreMalls(null);

        return new ResponseEntity<Mall>(updateMall, HttpStatus.OK)
    }  */

    // http://localhost:8080/api/malls/1
    @DeleteMapping("/malls/{id}")
    public ResponseEntity<HttpStatus>deleteMallById(@PathVariable("id")Long id){
        mallRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // http://localhost:8080/api/malls/4/forced/1
    @DeleteMapping("/malls/{id}/forced/{forced}")
    public ResponseEntity<HttpStatus> deleteMallByIdForced(@PathVariable("id") Long id, @PathVariable("forced") int forced) {

        if (forced==1) {
            Mall foundOwner = mallRepository.findById(id).
                    orElseThrow(()->new ResourceNotFoundException("Not found Mall with id="+id));
            for (New n: foundOwner.getNews()) {
                newRepository.deleteById(n.getId());
            }
        }
        mallRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/malls/{id}")
    public ResponseEntity<Mall> updateMall(@PathVariable("id") Long id, @RequestBody Mall mall){
        Mall foundMall=mallRepository.findById(id).get();
        if(mall.getName()!=null)
            foundMall.setName(mall.getName());
        if(mall.getLocation()!=null)
            foundMall.setLocation(mall.getLocation());
        Mall updateMall=mallRepository.save(foundMall);
        updateMall.setNews(null);
        return new ResponseEntity<Mall>(updateMall,HttpStatus.OK);
    }


}
