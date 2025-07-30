package com.vhouse.juniemvc.mappers;

import com.vhouse.juniemvc.dtos.BeerOrderLineDto;
import com.vhouse.juniemvc.entities.Beer;
import com.vhouse.juniemvc.entities.BeerOrder;
import com.vhouse.juniemvc.entities.BeerOrderLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * Mapper for converting between BeerOrderLine entity and BeerOrderLineDto
 */
@Mapper(componentModel = "spring")
public interface BeerOrderLineMapper {

    /**
     * Convert BeerOrderLine entity to BeerOrderLineDto
     * @param beerOrderLine The BeerOrderLine entity to convert
     * @return The converted BeerOrderLineDto
     */
    @Mapping(target = "beerOrderId", source = "beerOrder.id")
    @Mapping(target = "beerId", source = "beer.id")
    BeerOrderLineDto beerOrderLineToBeerOrderLineDto(BeerOrderLine beerOrderLine);

    /**
     * Convert BeerOrderLineDto to BeerOrderLine entity
     * @param beerOrderLineDto The BeerOrderLineDto to convert
     * @return The converted BeerOrderLine entity
     */
    @Mapping(target = "beerOrder", source = "beerOrderId", qualifiedByName = "beerOrderIdToBeerOrder")
    @Mapping(target = "beer", source = "beerId", qualifiedByName = "beerIdToBeer")
    BeerOrderLine beerOrderLineDtoToBeerOrderLine(BeerOrderLineDto beerOrderLineDto);

    /**
     * Update BeerOrderLine entity from BeerOrderLineDto
     * @param beerOrderLine The BeerOrderLine entity to update
     * @param beerOrderLineDto The BeerOrderLineDto with updated values
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "beerOrder", ignore = true)
    @Mapping(target = "beer", ignore = true)
    void updateBeerOrderLineFromDto(BeerOrderLineDto beerOrderLineDto, @MappingTarget BeerOrderLine beerOrderLine);

    /**
     * Convert a beer order ID to a BeerOrder entity
     * @param beerOrderId The beer order ID
     * @return A BeerOrder entity with the given ID
     */
    @Named("beerOrderIdToBeerOrder")
    default BeerOrder beerOrderIdToBeerOrder(Integer beerOrderId) {
        if (beerOrderId == null) {
            return null;
        }
        BeerOrder beerOrder = new BeerOrder();
        beerOrder.setId(beerOrderId);
        return beerOrder;
    }

    /**
     * Convert a beer ID to a Beer entity
     * @param beerId The beer ID
     * @return A Beer entity with the given ID
     */
    @Named("beerIdToBeer")
    default Beer beerIdToBeer(Integer beerId) {
        if (beerId == null) {
            return null;
        }
        Beer beer = new Beer();
        beer.setId(beerId);
        return beer;
    }
}