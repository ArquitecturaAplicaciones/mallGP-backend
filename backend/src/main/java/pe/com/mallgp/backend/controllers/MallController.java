package pe.com.mallgp.backend.controllers;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.mallgp.backend.entities.*;
import pe.com.mallgp.backend.repositories.AdminRepository;
import pe.com.mallgp.backend.repositories.MallRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MallController {
    @Autowired
    private MallRepository mallRepository;

    @GetMapping("/malls")
    public ResponseEntity<List<Mall>>getAllMall(){
        List<Mall>malls;
        malls=mallRepository.findAll();
        for (Mall m:malls){
            m.setNews(null);
        }
        return new ResponseEntity<List<Mall>>(malls, HttpStatus.OK);
    }

    @GetMapping("/mall_new")
    public ResponseEntity<List<Mall>>getAllMallAndNew(){
        List<Mall>malls;
        malls=mallRepository.findAll();
        for (Mall m:malls){
            for (New n:m.getNews()){
                n.setMall(null);
            }
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



    @PostMapping("/malls")
    public ResponseEntity<Mall> createMall(@RequestBody Mall mall){
        Mall newMall = mallRepository.save(new Mall(mall.getName(),mall.getLocation()));
        return new ResponseEntity<Mall>(newMall, HttpStatus.CREATED);
    }

    @GetMapping("/malls/{id}")
    public ResponseEntity<Mall> getMallById(@PathVariable("id")Long id){
        Mall mall=mallRepository.findById(id).get();
        mall.setNews(null);
        return new ResponseEntity<Mall>(mall,HttpStatus.OK);
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
