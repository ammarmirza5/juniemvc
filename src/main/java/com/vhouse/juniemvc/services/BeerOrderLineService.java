package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.dtos.BeerOrderLineDto;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for BeerOrderLine operations
 */
public interface BeerOrderLineService {

    /**
     * Get all beer order lines
     * @return List of all beer order lines
     */
    List<BeerOrderLineDto> getAllBeerOrderLines();

    /**
     * Get a beer order line by ID
     * @param id The beer order line ID
     * @return The beer order line with the given ID, or empty if not found
     */
    Optional<BeerOrderLineDto> getBeerOrderLineById(Integer id);

    /**
     * Get all beer order lines for a beer order
     * @param beerOrderId The beer order ID
     * @return List of beer order lines for the beer order
     */
    List<BeerOrderLineDto> getBeerOrderLinesByBeerOrderId(Integer beerOrderId);

    /**
     * Get all beer order lines for a beer
     * @param beerId The beer ID
     * @return List of beer order lines for the beer
     */
    List<BeerOrderLineDto> getBeerOrderLinesByBeerId(Integer beerId);

    /**
     * Save a new beer order line
     * @param beerOrderLineDto The beer order line to save
     * @return The saved beer order line
     */
    BeerOrderLineDto saveNewBeerOrderLine(BeerOrderLineDto beerOrderLineDto);

    /**
     * Update an existing beer order line
     * @param id The ID of the beer order line to update
     * @param beerOrderLineDto The updated beer order line data
     * @return The updated beer order line, or empty if not found
     */
    Optional<BeerOrderLineDto> updateBeerOrderLineById(Integer id, BeerOrderLineDto beerOrderLineDto);

    /**
     * Delete a beer order line by ID
     * @param id The ID of the beer order line to delete
     * @return True if the beer order line was deleted, false if not found
     */
    Boolean deleteBeerOrderLineById(Integer id);
}