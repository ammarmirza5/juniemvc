package com.vhouse.juniemvc.mappers;

import com.vhouse.juniemvc.dtos.CustomerDto;
import com.vhouse.juniemvc.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Customer entity and CustomerDto
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    /**
     * Convert Customer entity to CustomerDto
     * @param customer The Customer entity to convert
     * @return The converted CustomerDto
     */
    @Mapping(target = "beerOrderIds", source = "beerOrders", qualifiedByName = "beerOrdersToIds")
    CustomerDto customerToCustomerDto(Customer customer);

    /**
     * Convert CustomerDto to Customer entity
     * @param customerDto The CustomerDto to convert
     * @return The converted Customer entity
     */
    @Mapping(target = "beerOrders", ignore = true)
    Customer customerDtoToCustomer(CustomerDto customerDto);

    /**
     * Update Customer entity from CustomerDto
     * @param customer The Customer entity to update
     * @param customerDto The CustomerDto with updated values
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "beerOrders", ignore = true)
    void updateCustomerFromDto(CustomerDto customerDto, @MappingTarget Customer customer);

    /**
     * Convert a set of BeerOrder entities to a set of their IDs
     * @param beerOrders The set of BeerOrder entities
     * @return The set of BeerOrder IDs
     */
    @Named("beerOrdersToIds")
    default Set<Integer> beerOrdersToIds(Set<com.vhouse.juniemvc.entities.BeerOrder> beerOrders) {
        if (beerOrders == null) {
            return null;
        }
        return beerOrders.stream()
                .map(com.vhouse.juniemvc.entities.BeerOrder::getId)
                .collect(Collectors.toSet());
    }
}