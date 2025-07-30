package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.dtos.BeerOrderDto;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for BeerOrder operations
 */
public interface BeerOrderService {

    /**
     * Get all beer orders
     * @return List of all beer orders
     */
    List<BeerOrderDto> getAllBeerOrders();

    /**
     * Get a beer order by ID
     * @param id The beer order ID
     * @return The beer order with the given ID, or empty if not found
     */
    Optional<BeerOrderDto> getBeerOrderById(Integer id);

    /**
     * Get all beer orders for a customer
     * @param customerId The customer ID
     * @return List of beer orders for the customer
     */
    List<BeerOrderDto> getBeerOrdersByCustomerId(Integer customerId);

    /**
     * Save a new beer order
     * @param beerOrderDto The beer order to save
     * @return The saved beer order
     */
    BeerOrderDto saveNewBeerOrder(BeerOrderDto beerOrderDto);

    /**
     * Update an existing beer order
     * @param id The ID of the beer order to update
     * @param beerOrderDto The updated beer order data
     * @return The updated beer order, or empty if not found
     */
    Optional<BeerOrderDto> updateBeerOrderById(Integer id, BeerOrderDto beerOrderDto);

    /**
     * Delete a beer order by ID
     * @param id The ID of the beer order to delete
     * @return True if the beer order was deleted, false if not found
     */
    Boolean deleteBeerOrderById(Integer id);
}