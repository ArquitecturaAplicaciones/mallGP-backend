package pe.com.mallgp.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.mallgp.backend.entities.Mall;
import pe.com.mallgp.backend.entities.New;
import pe.com.mallgp.backend.excepctions.ResourceNotFoundException;
import pe.com.mallgp.backend.repositories.NewRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NewController {
    @Autowired
    private NewRepository newRepository;

    @GetMapping("/news")
    public ResponseEntity<List<New>> getAllNews(){
        List<New> news;
        news=newRepository.findAll();
        for(New n:news){
            n.setMall(null);
        }
        return new ResponseEntity<List<New>>(news, HttpStatus.OK);
    }

    @GetMapping("/news_date_on")
    public ResponseEntity<List<New>> getAllNewsAndDateOn(){
        List<New> news;
        news=newRepository.findAll();
        for(New n:news){
            n.setDate_on(null);
        }
        return new ResponseEntity<List<New>>(news, HttpStatus.OK);
    }

    @GetMapping("/news_date_off")
    public ResponseEntity<List<New>> getAllNewsAndDateOff(){
        List<New> news;
        news=newRepository.findAll();
        for(New n:news){
            n.setDate_of(null);
        }
        return new ResponseEntity<List<New>>(news, HttpStatus.OK);
    }

    @GetMapping("/news_mall")
    public ResponseEntity<List<New>> getAllNewsAndMall(){
        List<New> news;
        news=newRepository.findAll();
        for(New n:news){
            n.setMall(null);
        }
        return new ResponseEntity<List<New>>(news, HttpStatus.OK);
    }

    @PostMapping("/news")
    public ResponseEntity<New> createNew(@RequestBody New news){
        New newNew = newRepository.save(new New(news.getText(),news.getDate_on(),news.getDate_of(),news.getMall()));
        return new ResponseEntity<New>(newNew, HttpStatus.CREATED);
    }


    // http://localhost:8080/api/news/1
    @DeleteMapping("/news/{id}")
    public ResponseEntity<HttpStatus>deleteNewById(@PathVariable("id")Long id){
        newRepository.deleteById(id);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // http://localhost:8080/api/news/4/forced/1
    @DeleteMapping("/news/{id}/forced/{forced}")
    public ResponseEntity<HttpStatus> deleteNewByIdForced(@PathVariable("id") Long id, @PathVariable("forced") int forced) {

        if (forced==1) {
            New foundOwner = newRepository.findById(id).
                    orElseThrow(()->new ResourceNotFoundException("Not found New with id="+id));

        }
        newRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/news/{id}")
    public ResponseEntity<New> updateNew(@PathVariable("id") Long id, @RequestBody New news){
        New foundNew=newRepository.findById(id).get();
        if(news.getText()!=null)
            foundNew.setText(news.getText());
        if(news.getDate_on()!=null)
            foundNew.setDate_on(news.getDate_on());
        if(news.getDate_of()!=null)
            foundNew.setDate_of(news.getDate_of());
        if(news.getMall()!=null)
            foundNew.setMall(news.getMall());
        New updateNew=newRepository.save(foundNew);
        updateNew.setMall(null);
        return new ResponseEntity<New>(updateNew,HttpStatus.OK);
    }


}
