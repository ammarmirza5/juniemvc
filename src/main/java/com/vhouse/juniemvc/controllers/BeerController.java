package com.vhouse.juniemvc.controllers;

import com.vhouse.juniemvc.dtos.BeerDto;
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
    public ResponseEntity<List<BeerDto>> listBeers() {
        return new ResponseEntity<>(beerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") Integer id) {
        Optional<BeerDto> beerOptional = beerService.findById(id);
        
        return beerOptional
                .map(beerDto -> new ResponseEntity<>(beerDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<BeerDto> createBeer(@RequestBody BeerDto beerDto) {
        BeerDto savedBeerDto = beerService.save(beerDto);
        return new ResponseEntity<>(savedBeerDto, HttpStatus.CREATED);
    }
    
    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDto> updateBeer(@PathVariable("beerId") Integer id, @RequestBody BeerDto beerDto) {
        if (!beerService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        BeerDto updatedBeerDto = beerService.update(id, beerDto);
        return new ResponseEntity<>(updatedBeerDto, HttpStatus.OK);
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