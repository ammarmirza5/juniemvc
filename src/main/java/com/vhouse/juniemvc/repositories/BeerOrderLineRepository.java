package com.vhouse.juniemvc.repositories;

import com.vhouse.juniemvc.entities.Beer;
import com.vhouse.juniemvc.entities.BeerOrder;
import com.vhouse.juniemvc.entities.BeerOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeerOrderLineRepository extends JpaRepository<BeerOrderLine, Integer> {
    
    /**
     * Find all beer order lines for a specific beer order
     * @param beerOrder The beer order to find lines for
     * @return List of beer order lines for the beer order
     */
    List<BeerOrderLine> findAllByBeerOrder(BeerOrder beerOrder);
    
    /**
     * Find all beer order lines for a specific beer order ID
     * @param beerOrderId The beer order ID to find lines for
     * @return List of beer order lines for the beer order ID
     */
    List<BeerOrderLine> findAllByBeerOrderId(Integer beerOrderId);
    
    /**
     * Find all beer order lines for a specific beer
     * @param beer The beer to find order lines for
     * @return List of beer order lines for the beer
     */
    List<BeerOrderLine> findAllByBeer(Beer beer);
    
    /**
     * Find all beer order lines for a specific beer ID
     * @param beerId The beer ID to find order lines for
     * @return List of beer order lines for the beer ID
     */
    List<BeerOrderLine> findAllByBeerId(Integer beerId);
}