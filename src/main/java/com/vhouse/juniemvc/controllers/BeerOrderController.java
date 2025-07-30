package com.vhouse.juniemvc.controllers;

import com.vhouse.juniemvc.dtos.BeerOrderDto;
import com.vhouse.juniemvc.services.BeerOrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for BeerOrder operations
 */
@RestController
@RequestMapping("/api/v1/beer-orders")
public class BeerOrderController {

    private final BeerOrderService beerOrderService;

    public BeerOrderController(BeerOrderService beerOrderService) {
        this.beerOrderService = beerOrderService;
    }

    /**
     * Get all beer orders
     * @return List of all beer orders
     */
    @GetMapping
    public List<BeerOrderDto> getAllBeerOrders() {
        return beerOrderService.getAllBeerOrders();
    }

    /**
     * Get a beer order by ID
     * @param orderId The beer order ID
     * @return The beer order with the given ID
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<BeerOrderDto> getBeerOrderById(@PathVariable("orderId") Integer orderId) {
        return beerOrderService.getBeerOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all beer orders for a customer
     * @param customerId The customer ID
     * @return List of beer orders for the customer
     */
    @GetMapping("/customer/{customerId}")
    public List<BeerOrderDto> getBeerOrdersByCustomerId(@PathVariable("customerId") Integer customerId) {
        return beerOrderService.getBeerOrdersByCustomerId(customerId);
    }

    /**
     * Create a new beer order
     * @param beerOrderDto The beer order to create
     * @return The created beer order
     */
    @PostMapping
    public ResponseEntity<BeerOrderDto> createBeerOrder(@RequestBody BeerOrderDto beerOrderDto) {
        BeerOrderDto savedBeerOrder = beerOrderService.saveNewBeerOrder(beerOrderDto);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer-orders/" + savedBeerOrder.getId());
        
        return new ResponseEntity<>(savedBeerOrder, headers, HttpStatus.CREATED);
    }

    /**
     * Update an existing beer order
     * @param orderId The ID of the beer order to update
     * @param beerOrderDto The updated beer order data
     * @return The updated beer order
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<BeerOrderDto> updateBeerOrder(
            @PathVariable("orderId") Integer orderId,
            @RequestBody BeerOrderDto beerOrderDto) {
        
        return beerOrderService.updateBeerOrderById(orderId, beerOrderDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a beer order by ID
     * @param orderId The ID of the beer order to delete
     * @return No content if successful, not found if the beer order doesn't exist
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteBeerOrder(@PathVariable("orderId") Integer orderId) {
        if (beerOrderService.deleteBeerOrderById(orderId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}