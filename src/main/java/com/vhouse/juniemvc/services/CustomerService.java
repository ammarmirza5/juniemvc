package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.dtos.CustomerDto;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Customer operations
 */
public interface CustomerService {

    /**
     * Get all customers
     * @return List of all customers
     */
    List<CustomerDto> getAllCustomers();

    /**
     * Get a customer by ID
     * @param id The customer ID
     * @return The customer with the given ID, or empty if not found
     */
    Optional<CustomerDto> getCustomerById(Integer id);

    /**
     * Save a new customer
     * @param customerDto The customer to save
     * @return The saved customer
     */
    CustomerDto saveNewCustomer(CustomerDto customerDto);

    /**
     * Update an existing customer
     * @param id The ID of the customer to update
     * @param customerDto The updated customer data
     * @return The updated customer, or empty if not found
     */
    Optional<CustomerDto> updateCustomerById(Integer id, CustomerDto customerDto);

    /**
     * Delete a customer by ID
     * @param id The ID of the customer to delete
     * @return True if the customer was deleted, false if not found
     */
    Boolean deleteCustomerById(Integer id);
}