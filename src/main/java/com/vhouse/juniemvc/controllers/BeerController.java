package com.vhouse.juniemvc.controllers;

import com.vhouse.juniemvc.entities.Beer;
import com.vhouse.juniemvc.services.BeerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping
    public ResponseEntity<List<Beer>> listBeers() {
        return new ResponseEntity<>(beerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("beerId") Integer id) {
        Optional<Beer> beerOptional = beerService.findById(id);
        
        return beerOptional
                .map(beer -> new ResponseEntity<>(beer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Beer> createBeer(@RequestBody Beer beer) {
        Beer savedBeer = beerService.save(beer);
        return new ResponseEntity<>(savedBeer, HttpStatus.CREATED);
    }
    
    @PutMapping("/{beerId}")
    public ResponseEntity<Beer> updateBeer(@PathVariable("beerId") Integer id, @RequestBody Beer beer) {
        if (!beerService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Beer updatedBeer = beerService.update(id, beer);
        return new ResponseEntity<>(updatedBeer, HttpStatus.OK);
    }
    
    @DeleteMapping("/{beerId}")
    public ResponseEntity<Void> deleteBeer(@PathVariable("beerId") Integer id) {
        if (!beerService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        beerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}