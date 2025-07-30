package com.vhouse.juniemvc.controllers;

import com.vhouse.juniemvc.dtos.BeerOrderLineDto;
import com.vhouse.juniemvc.services.BeerOrderLineService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for BeerOrderLine operations
 */
@RestController
@RequestMapping("/api/v1/beer-order-lines")
public class BeerOrderLineController {

    private final BeerOrderLineService beerOrderLineService;

    public BeerOrderLineController(BeerOrderLineService beerOrderLineService) {
        this.beerOrderLineService = beerOrderLineService;
    }

    /**
     * Get all beer order lines
     * @return List of all beer order lines
     */
    @GetMapping
    public List<BeerOrderLineDto> getAllBeerOrderLines() {
        return beerOrderLineService.getAllBeerOrderLines();
    }

    /**
     * Get a beer order line by ID
     * @param lineId The beer order line ID
     * @return The beer order line with the given ID
     */
    @GetMapping("/{lineId}")
    public ResponseEntity<BeerOrderLineDto> getBeerOrderLineById(@PathVariable("lineId") Integer lineId) {
        return beerOrderLineService.getBeerOrderLineById(lineId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all beer order lines for a beer order
     * @param beerOrderId The beer order ID
     * @return List of beer order lines for the beer order
     */
    @GetMapping("/order/{beerOrderId}")
    public List<BeerOrderLineDto> getBeerOrderLinesByBeerOrderId(@PathVariable("beerOrderId") Integer beerOrderId) {
        return beerOrderLineService.getBeerOrderLinesByBeerOrderId(beerOrderId);
    }

    /**
     * Get all beer order lines for a beer
     * @param beerId The beer ID
     * @return List of beer order lines for the beer
     */
    @GetMapping("/beer/{beerId}")
    public List<BeerOrderLineDto> getBeerOrderLinesByBeerId(@PathVariable("beerId") Integer beerId) {
        return beerOrderLineService.getBeerOrderLinesByBeerId(beerId);
    }

    /**
     * Create a new beer order line
     * @param beerOrderLineDto The beer order line to create
     * @return The created beer order line
     */
    @PostMapping
    public ResponseEntity<BeerOrderLineDto> createBeerOrderLine(@RequestBody BeerOrderLineDto beerOrderLineDto) {
        BeerOrderLineDto savedBeerOrderLine = beerOrderLineService.saveNewBeerOrderLine(beerOrderLineDto);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer-order-lines/" + savedBeerOrderLine.getId());
        
        return new ResponseEntity<>(savedBeerOrderLine, headers, HttpStatus.CREATED);
    }

    /**
     * Update an existing beer order line
     * @param lineId The ID of the beer order line to update
     * @param beerOrderLineDto The updated beer order line data
     * @return The updated beer order line
     */
    @PutMapping("/{lineId}")
    public ResponseEntity<BeerOrderLineDto> updateBeerOrderLine(
            @PathVariable("lineId") Integer lineId,
            @RequestBody BeerOrderLineDto beerOrderLineDto) {
        
        return beerOrderLineService.updateBeerOrderLineById(lineId, beerOrderLineDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a beer order line by ID
     * @param lineId The ID of the beer order line to delete
     * @return No content if successful, not found if the beer order line doesn't exist
     */
    @DeleteMapping("/{lineId}")
    public ResponseEntity<Void> deleteBeerOrderLine(@PathVariable("lineId") Integer lineId) {
        if (beerOrderLineService.deleteBeerOrderLineById(lineId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}