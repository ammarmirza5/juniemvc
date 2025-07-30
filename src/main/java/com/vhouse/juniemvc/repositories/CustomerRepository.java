package com.vhouse.juniemvc.repositories;

import com.vhouse.juniemvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Add custom query methods if needed
}