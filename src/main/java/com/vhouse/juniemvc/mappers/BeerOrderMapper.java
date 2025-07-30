package com.vhouse.juniemvc.mappers;

import com.vhouse.juniemvc.dtos.BeerOrderDto;
import com.vhouse.juniemvc.dtos.BeerOrderLineDto;
import com.vhouse.juniemvc.entities.BeerOrder;
import com.vhouse.juniemvc.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;

/**
 * Mapper for converting between BeerOrder entity and BeerOrderDto
 */
@Mapper(componentModel = "spring", uses = {BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    /**
     * Convert BeerOrder entity to BeerOrderDto
     * @param beerOrder The BeerOrder entity to convert
     * @return The converted BeerOrderDto
     */
    @Mapping(target = "customerId", source = "customer.id")
    BeerOrderDto beerOrderToBeerOrderDto(BeerOrder beerOrder);

    /**
     * Convert BeerOrderDto to BeerOrder entity
     * @param beerOrderDto The BeerOrderDto to convert
     * @return The converted BeerOrder entity
     */
    @Mapping(target = "customer", source = "customerId", qualifiedByName = "customerIdToCustomer")
    BeerOrder beerOrderDtoToBeerOrder(BeerOrderDto beerOrderDto);

    /**
     * Update BeerOrder entity from BeerOrderDto
     * @param beerOrder The BeerOrder entity to update
     * @param beerOrderDto The BeerOrderDto with updated values
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "customer", ignore = true)
    void updateBeerOrderFromDto(BeerOrderDto beerOrderDto, @MappingTarget BeerOrder beerOrder);

    /**
     * Convert a customer ID to a Customer entity
     * @param customerId The customer ID
     * @return A Customer entity with the given ID
     */
    @Named("customerIdToCustomer")
    default Customer customerIdToCustomer(Integer customerId) {
        if (customerId == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;
    }
}