package pe.com.mallgp.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.mallgp.backend.entities.Suggestion;
import pe.com.mallgp.backend.services.SuggestionService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;

    @GetMapping("/suggestions")
    public ResponseEntity<List<Suggestion>>getAllSuggestion(){
        List<Suggestion>suggestions=suggestionService.listAll();
        return new ResponseEntity<List<Suggestion>>(suggestions, HttpStatus.OK);
    }

    @PostMapping("/suggestions")
    public ResponseEntity<Suggestion> createSuggestion(@RequestBody Suggestion suggestion){
        Suggestion newSuggestion=suggestionService.save(new Suggestion(suggestion.getNsugerencia(),suggestion.getContenido(),suggestion.getDatesugerencia()));
        return new ResponseEntity<Suggestion>(newSuggestion, HttpStatus.CREATED);
    }

    @DeleteMapping("/suggestions/{id}/forced/{forced}")
    public ResponseEntity<HttpStatus> deleteSuggestionByIdForced(@PathVariable("id") Long id, @PathVariable("forced") int forced){
        suggestionService.delete(id, forced);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/suggestions/{id}")
    public ResponseEntity<Suggestion>getSuggestionById(@PathVariable("id")Long id){
        Suggestion suggestion=suggestionService.findById(id);
        return new ResponseEntity<Suggestion>(suggestion,HttpStatus.OK);
    }
}
