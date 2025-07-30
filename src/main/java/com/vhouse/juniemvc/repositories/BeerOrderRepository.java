package com.vhouse.juniemvc.repositories;

import com.vhouse.juniemvc.entities.BeerOrder;
import com.vhouse.juniemvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, Integer> {
    
    /**
     * Find all beer orders for a specific customer
     * @param customer The customer to find orders for
     * @return List of beer orders for the customer
     */
    List<BeerOrder> findAllByCustomer(Customer customer);
    
    /**
     * Find all beer orders for a specific customer ID
     * @param customerId The customer ID to find orders for
     * @return List of beer orders for the customer ID
     */
    List<BeerOrder> findAllByCustomerId(Integer customerId);
}