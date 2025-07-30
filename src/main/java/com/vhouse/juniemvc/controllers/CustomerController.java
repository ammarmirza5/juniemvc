package com.vhouse.juniemvc.controllers;

import com.vhouse.juniemvc.dtos.CustomerDto;
import com.vhouse.juniemvc.services.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Customer operations
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Get all customers
     * @return List of all customers
     */
    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * Get a customer by ID
     * @param customerId The customer ID
     * @return The customer with the given ID
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomerById(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new customer
     * @param customerDto The customer to create
     * @return The created customer
     */
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto savedCustomer = customerService.saveNewCustomer(customerDto);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + savedCustomer.getId());
        
        return new ResponseEntity<>(savedCustomer, headers, HttpStatus.CREATED);
    }

    /**
     * Update an existing customer
     * @param customerId The ID of the customer to update
     * @param customerDto The updated customer data
     * @return The updated customer
     */
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable("customerId") Integer customerId,
            @RequestBody CustomerDto customerDto) {
        
        return customerService.updateCustomerById(customerId, customerDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a customer by ID
     * @param customerId The ID of the customer to delete
     * @return No content if successful, not found if the customer doesn't exist
     */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        if (customerService.deleteCustomerById(customerId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}